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
        /* passing in a lambda to the launch coroutine builder */
      viewModelScope.launch {
          tonight.value = getTonightFromDatabase()
      }


    }
/*   A suspending function is simply a function that can be paused and resumed at a later time.
        They can execute a long running operation and wait for it to complete without blocking.

       syntax:  It can take a parameter and have a return type.
                However, suspending functions can only be invoked by another suspending function or within a coroutine.
        */
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