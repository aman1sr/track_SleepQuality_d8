package com.example.track_sleepquality.sleeptracker

import android.app.Application
import android.provider.SyncStateContract.Helpers.insert
import androidx.lifecycle.*
import com.example.track_sleepquality.database.SleepDatabase
import com.example.track_sleepquality.database.SleepDatabaseDao
import com.example.track_sleepquality.database.SleepNight
import com.example.track_sleepquality.formatNights
import kotlinx.coroutines.launch

class SleepTrackerViewModel(
    val database: SleepDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var nights = database.getAllNights()

    val nightsString = Transformations.map(nights){ nights ->
        formatNights(nights, application.resources)
    }

    private var tonight = MutableLiveData<SleepNight?>()

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
      viewModelScope.launch {
          tonight.value = getTonightFromDatabase()
      }


    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        var night = database.getTonight()
        if (night?.endTimeMilli != night?.startTimeMilli) {
            night = null
        }
        return night
    }

    fun onStartTracking() {
        viewModelScope.launch {
            val newNight = SleepNight()
            insert(newNight)
            tonight.value = getTonightFromDatabase()
        }
    }
    private suspend fun insert(night: SleepNight) {
        database.insert(night)
    }

    fun onStopTracking() {
        viewModelScope.launch {
            val oldNight = tonight.value?: return@launch
            oldNight.endTimeMilli = System.currentTimeMillis()
            update(oldNight)
        }
    }

    private suspend fun update(night: SleepNight) {
        database.update(night)
    }

    fun onClear() {
        viewModelScope.launch {
            clear()
            tonight.value = null
        }
    }

    suspend fun clear() {
        database.clear()
    }


}