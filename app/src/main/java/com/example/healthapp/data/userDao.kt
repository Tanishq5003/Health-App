package com.example.healthapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface userDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAlarm(alarm: user)

    @Query("SELECT * FROM Alarm_Data ORDER BY id ASC")
    fun readAllData(): LiveData<List<user>>

}