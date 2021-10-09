package com.mylongkenkai.drivesafe.data

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mylongkenkai.drivesafe.R

class RecordAdapter (
    private val recordsList : ArrayList<Record>
    ) : RecyclerView.Adapter<RecordAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateAndTimeView : TextView
        val inActivityView : TextView
        init {
            // Define click listener for the ViewHolder's View.
            dateAndTimeView = itemView.findViewById<TextView>(R.id.dateTime_item)
            inActivityView = itemView.findViewById<TextView>(R.id.inactivity_item)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_log, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDateAndTime = recordsList[position]
        //val currentInActivity = recordsList[position]

        val dateTextView = holder.dateAndTimeView
        //val activityTextView = holder.inActivityView

        dateTextView.setText(currentDateAndTime.dateTime)
        //activityTextView.setText(currentInActivity.inActivity.toString())
    }

    override fun getItemCount(): Int {
        return recordsList.size
    }
}