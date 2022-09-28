package com.example.track_sleepquality.sleeptracker

import android.app.Application
import androidx.lifecycle.*
import com.example.track_sleepquality.database.SleepDatabase
import com.example.track_sleepquality.database.SleepDatabaseDao
import com.example.track_sleepquality.database.SleepNight
import com.example.track_sleepquality.formatNights

class SleepTrackerViewModel(
    val database: SleepDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private val nights = database.getAllNights()

    val nightsString = Transformations.map(nights){ nights ->
        formatNights(nights, application.resources)
    }

    private var tonight = MutableLiveData<SleepNight?>()

    init {
        initializeTonight()
    }

    private fun initializeTonight() {

    }

    fun onStartTracking() {

    }
    fun onStopTracking() {

    }
    fun onClear() {

    }


}