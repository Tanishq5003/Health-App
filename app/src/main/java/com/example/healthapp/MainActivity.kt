package com.example.healthapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthapp.data.alarmViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var fab: ExtendedFloatingActionButton
    lateinit var mViewModel : alarmViewModel
    lateinit var recv : RecyclerView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = AlarmAdapter()
        recv = findViewById(R.id.recycle)
        recv.adapter = adapter
        recv.layoutManager = LinearLayoutManager(this)

        mViewModel = ViewModelProvider(this).get(alarmViewModel::class.java)
        mViewModel.readAllData.observe(this, Observer { user ->
            adapter.setData(user)
        })




        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, AlarmSetter::class.java)
            startActivity(intent)
        }






    }
}