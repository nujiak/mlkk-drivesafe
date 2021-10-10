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
        val recordTypeTextView : TextView = itemView.findViewById(R.id.record_type)
        val dateTimeTextView : TextView = itemView.findViewById(R.id.record_date_time)
    }

    override fun getItemId(position: Int): Long {
        return recordsList[position].entryId.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.record_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = recordsList[position]

        val dateTimeTextView = holder.dateTimeTextView
        val recordTypeTextView = holder.recordTypeTextView

        recordTypeTextView.text = recordTypeTextView.resources.getString(record.type.text)
        dateTimeTextView.text = record.dateTime.toString()
    }

    override fun getItemCount(): Int {
        return recordsList.size
    }
}