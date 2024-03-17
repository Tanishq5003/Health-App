package com.example.healthapp.BroadcastReciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.healthapp.textToSpechService.ttsService

class alarmReciever:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var med: String? = intent?.getStringExtra("med")
        val desc:String? = intent?.getStringExtra("desc")

        val serviceIntent = Intent(context, ttsService::class.java)
        serviceIntent.putExtra("med", med)
        serviceIntent.putExtra("desc", desc)
        Log.d("med", "$med")
        Log.d("desc", "$desc")
        Log.d("starting service", "service started")
        context?.startService(serviceIntent)

    }
}