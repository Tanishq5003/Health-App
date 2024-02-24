package com.example.healthapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.healthapp.data.alarmViewModel
import com.example.healthapp.data.user
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.Dispatchers

class AlarmAdapter: RecyclerView.Adapter<AlarmAdapter.alarnViewHolder>() {
    var alarmList = emptyList<user>()

    private lateinit var mListner: onItemClickListner

    interface onItemClickListner {
        fun onItemClick(pos: Int)
    }

    fun setonItemClickListner(listner: onItemClickListner) {
        mListner = listner
    }


    class alarnViewHolder(val v: View, listner: onItemClickListner) : RecyclerView.ViewHolder(v) {
        val time = v.findViewById<MaterialTextView>(R.id.time)
        val med = v.findViewById<MaterialTextView>(R.id.med)
        val toggle = v.findViewById<SwitchMaterial>(R.id.toggle)

        init {
            toggle.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): alarnViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.alarm_card, parent, false)
        return alarnViewHolder(v, mListner)
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }

    override fun onBindViewHolder(holder: alarnViewHolder, position: Int) {

        val currentItem = alarmList[position]
        holder.time.text = currentItem.time
        holder.med.text = currentItem.med
        holder.toggle.setOnClickListener {
            mListner.onItemClick(position)
        }

    }

    fun setData(user: List<user>) {
        this.alarmList = user
        notifyDataSetChanged()
    }
}