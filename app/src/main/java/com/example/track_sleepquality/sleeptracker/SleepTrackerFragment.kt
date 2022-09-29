package com.example.track_sleepquality.sleeptracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.track_sleepquality.R
import com.example.track_sleepquality.database.SleepDatabase
import com.example.track_sleepquality.databinding.FragmentSleepTrackerBinding

class SleepTrackerFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentSleepTrackerBinding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_sleep_tracker,container,false
        )

        /* requireNotNull Kotlin function throws an IllegalArgumentException if the value is null. */
        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)

        val sleepTrackerViewModel = ViewModelProvider(this,
        viewModelFactory).get(SleepTrackerViewModel::class.java)

        binding.setLifecycleOwner(this)

        binding.sleepTrackerViewModel = sleepTrackerViewModel


        // Inflate the layout for this fragment
        return binding.root

    }


}