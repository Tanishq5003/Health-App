package com.example.healthapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Alarm_Data")
data class user(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val time: String,
    val med: String,
    val desc: String,
    val status: Int
)
