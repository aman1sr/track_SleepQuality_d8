package com.example.track_sleepquality.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/*
* the DAO provides convenience methods for inserting, deleting, and updating the database.
*
* Room library provides convenience annotations- @Insert, @Update, ...
* */
@Dao
interface SleepDatabaseDao {

    @Insert
    suspend fun insert(night: SleepNight)

    @Update
    suspend fun update(night: SleepNight)

    @Query("SELECT * from daily_sleep_quality_table WHERE nightId = :key")
    suspend fun get(key: Long): SleepNight?

    @Query("DELETE FROM daily_sleep_quality_table")
    suspend fun clear()

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    suspend fun getTonight(): SleepNight?

    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
    fun getAllNights(): LiveData<List<SleepNight>>
}