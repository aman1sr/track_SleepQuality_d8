package com.example.track_sleepquality

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.track_sleepquality.database.SleepDatabase
import com.example.track_sleepquality.database.SleepDatabaseDao
import com.example.track_sleepquality.database.SleepNight
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/*
* @RunWith annotation identifies the test runner, which is the program that sets up and executes the tests.
* */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var sleepDao: SleepDatabaseDao
    private lateinit var db: SleepDatabaseDao


    /* it creates an in-memory SleepDatabase with the SleepDatabaseDao.
            "In-memory" means that this database is not saved on the file system and will be deleted after the tests run.
            */
    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.

        db = Room.inMemoryDatabaseBuilder(context, SleepDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()

        sleepDao = (db as SleepDatabase).sleepDatabaseDao

    }
/*
* When testing is done, the function annotated with @After executes to close the database.
* */
    @After
    @kotlin.jvm.Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    /*
    * you create, insert, and retrieve a SleepNight, and assert that they are the same.
    *   If anything goes wrong, throw an exception
    * */
    @Test
    @kotlin.jvm.Throws(Exception::class)
    suspend fun insertAndGetNight() {
        val night = SleepNight()
        sleepDao.insert(night)
        val tonight = sleepDao.getTonight()
        assertEquals(tonight?.sleepQuality,-1)

    }

}