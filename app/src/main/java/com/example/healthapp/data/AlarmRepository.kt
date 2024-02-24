package com.example.healthapp.data

import androidx.lifecycle.LiveData

class AlarmRepository(private val userDao: userDao) {
    val readAllData: LiveData<List<user>> = userDao.readAllData()
    suspend fun addAlarm(user: user){
        userDao.addAlarm(user)
    }

    suspend fun updateStatus(entityId: Int, newStatus: Int){
        userDao.updateStatus(entityId, newStatus)
    }
}