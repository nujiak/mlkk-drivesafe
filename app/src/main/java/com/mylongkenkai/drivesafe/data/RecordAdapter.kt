package com.mylongkenkai.drivesafe.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mylongkenkai.drivesafe.R

class RecordAdapter (
    private val recordsList : List<Record>
    ) : RecyclerView.Adapter<RecordAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recordTypeView : TextView = itemView.findViewById(R.id.record_type)
        val dateAndTimeView : TextView = itemView.findViewById(R.id.record_date_time)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.record_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = recordsList[position]

        val dateTextView = holder.dateAndTimeView

        holder.recordTypeView.text = record.type.text
        dateTextView.text = record.dateTime.toString()
    }

    override fun getItemCount(): Int {
        return recordsList.size
    }
}