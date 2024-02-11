package com.example.healthapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.healthapp.data.user
import com.google.android.material.textview.MaterialTextView

class AlarmAdapter: RecyclerView.Adapter<AlarmAdapter.alarnViewHolder>() {
    private var alarmList = emptyList<user>()
    class alarnViewHolder(val v: View): RecyclerView.ViewHolder(v){
        val time = v.findViewById<MaterialTextView>(R.id.time)
        val med = v.findViewById<MaterialTextView>(R.id.med)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): alarnViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.alarm_card, parent, false)
        return  alarnViewHolder(v)
    }

    override fun getItemCount(): Int {
       return alarmList.size
    }

    override fun onBindViewHolder(holder: alarnViewHolder, position: Int) {
        val currentItem = alarmList[position]
        holder.time.text = currentItem.time
        holder.med.text = currentItem.med
    }
    fun setData(user: List<user>){
        this.alarmList = user
        notifyDataSetChanged()
    }
}