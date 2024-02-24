package com.example.healthapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class alarmViewModel(application: Application): AndroidViewModel(application) {

  val readAllData: LiveData<List<user>>
    private val repository: AlarmRepository

    init {
        val userDao = AlarmDatabase.getDatabase(application).userDao()
        repository = AlarmRepository(userDao)
        readAllData = repository.readAllData
    }
    fun addAlarm(user: user){
        viewModelScope.launch(Dispatchers.IO){
            repository.addAlarm(user)
        }

    }

    fun updateStatus(entityId: Int, newStatus: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateStatus(entityId, newStatus)
        }
    }


}