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
import com.example.healthapp.data.userDao
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class MainActivity : AppCompatActivity(), AlarmAdapter.onItemClickListner {
    lateinit var fab: ExtendedFloatingActionButton
    lateinit var mViewModel : alarmViewModel
    lateinit var recv : RecyclerView
    lateinit var adapter: AlarmAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = AlarmAdapter()
        recv = findViewById(R.id.recycle)
        recv.adapter = adapter
        recv.layoutManager = LinearLayoutManager(this)

        mViewModel = ViewModelProvider(this).get(alarmViewModel::class.java)
        mViewModel.readAllData.observe(this, Observer { user ->
            adapter.setData(user)
        })

        adapter.setonItemClickListner(this)


        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, AlarmSetter::class.java)
            startActivity(intent)
        }






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