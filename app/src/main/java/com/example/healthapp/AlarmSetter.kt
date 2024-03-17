package com.example.healthapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.healthapp.data.alarmViewModel
import com.example.healthapp.data.user
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

@Suppress("DEPRECATION")
class AlarmSetter : AppCompatActivity() {
    lateinit var time : TextInputLayout
    lateinit var med: TextInputLayout
    lateinit var desc: TextInputLayout
    lateinit var submit: Button
    lateinit var mViewModel: alarmViewModel
    lateinit var calender: Calendar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_setter)
        mViewModel = ViewModelProvider(this).get(alarmViewModel::class.java)
        time = findViewById(R.id.time)
        time.editText?.setOnClickListener {
            openTimePicker()
        }
        med = findViewById(R.id.medicine)
        desc = findViewById(R.id.dosage)
        submit = findViewById(R.id.button)
        submit.setOnClickListener {

            insertDataToDatabase()

        }
    }
    private fun openTimePicker(){
        val clockFormat = TimeFormat.CLOCK_12H
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Set Alarm Time")
            .build()
        picker.show(supportFragmentManager, "tag")
        picker.addOnPositiveButtonClickListener {
            var hour = picker.hour
            var min = picker.minute
            time.editText?.setText("$hour : $min")
            calender = Calendar.getInstance()
            calender.set(Calendar.HOUR_OF_DAY, picker.hour)
            calender.set(Calendar.MINUTE, picker.minute)
            calender.set(Calendar.SECOND, 0)
            calender.set(Calendar.MILLISECOND, 0)
        }
    }
    private fun insertDataToDatabase(){
        val time_text = time.editText?.text.toString()
        val med_text = med.editText?.text.toString()
        val desc_text = desc.editText?.text.toString()
        val millisecond: Long = calender.timeInMillis
        if(inputCheck(time_text, med_text, desc_text)){
            val alarm = user(0, time_text, med_text, desc_text, 1)
            mViewModel.addAlarm(alarm)
            Toast.makeText(this, "Alarm Added", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        else{
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(time: String, med: String, desc: String):Boolean{
        return !(TextUtils.isEmpty(time) && TextUtils.isEmpty(med) && TextUtils.isEmpty(desc))
    }
}