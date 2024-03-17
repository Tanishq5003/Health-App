package com.example.healthapp

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthapp.BroadcastReciever.alarmReciever
import com.example.healthapp.data.alarmViewModel
import com.example.healthapp.data.user
import com.example.healthapp.recyclerViewAdapter.AlarmAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class MainActivity : AppCompatActivity(), AlarmAdapter.onItemClickListner {
    lateinit var fab: ExtendedFloatingActionButton
    lateinit var mViewModel : alarmViewModel
    lateinit var recv : RecyclerView
    lateinit var adapter: AlarmAdapter
    lateinit var alarmManager: AlarmManager
    lateinit var pendingIntent: PendingIntent
    lateinit var array: ArrayList<user>


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        array = arrayListOf()
        adapter = AlarmAdapter()
        recv = findViewById(R.id.recycle)
        recv.adapter = adapter
        recv.layoutManager = LinearLayoutManager(this)

        mViewModel = ViewModelProvider(this).get(alarmViewModel::class.java)
        mViewModel.readAllData.observe(this, Observer { user ->
            adapter.setData(user)
            array.clear()
            array.addAll(user)
            for (data in array) {

                Log.d("entered loop", "Looping for alarm")
                val dateString = data.time
                val timePending: Long = convertTimeToMillis(dateString)
                Log.d("time", "$timePending")
                alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
                val intent = Intent(this, alarmReciever::class.java)
                intent.putExtra("med", data.med)
                intent.putExtra("desc", data.desc)
                pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE)



                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP, timePending, pendingIntent
                )

                Log.d("Alarm Set", "time : $dateString")

            }
        })

        adapter.setonItemClickListner(this)


//        Code for alarm Setting -- Using FoxAndroid , Gemini and Copilot - use for loop


//        for (data in array) {
//
//            Log.d("entered loop", "Looping for alarm")
//            val dateString = data.time
//            val timePending: Long = convertTimeToMillis(dateString)
//            alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
//            val intent = Intent(this, alarmReciever::class.java)
//            intent.putExtra("med", data.med)
//            intent.putExtra("desc", data.desc)
//            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE)
//
//
//
//        alarmManager.setRepeating(
//            AlarmManager.RTC_WAKEUP, timePending, AlarmManager.INTERVAL_DAY, pendingIntent
//        )
//
//            Log.d("Alarm Set", "time : $dateString")
//
//        }
//          Code for alarm setter ends
        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            Log.d("fab", "fabi")
            val intent = Intent(this, AlarmSetter::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }




    }

    private fun convertTimeToMillis(time: String): Long {
        val parts = time.split(" : ").map { it.toInt() } // Split and convert to Int
        if (parts.size != 2 || parts[0] !in 0..23 || parts[1] !in 0..59) {
            throw IllegalArgumentException("Invalid time format. Expected hh:mm")
        }
        return (parts[0] * 60 * 60 * 1000L) + (parts[1] * 60 * 1000L)
    }


    override fun onItemClick(pos: Int){
        val user = adapter.alarmList[pos]
        val state: Int = user.status
        if (state == 1){
            mViewModel.updateStatus(user.id, 0)
            adapter.notifyItemChanged(user.id)
        }
        else{
            mViewModel.updateStatus(user.id, 1)
            adapter.notifyItemChanged(user.id)
        }
    }
}