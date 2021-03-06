package com.mylongkenkai.drivesafe.data

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mylongkenkai.drivesafe.R


class ExclusionAdapter(
    private val exclusions: List<Exclusion>,
    private val delete: (Exclusion) -> Unit
) : RecyclerView.Adapter<ExclusionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val phoneTextView : TextView
        val deleteButton : Button
        init {
            // Define click listener for the ViewHolder's View.
            phoneTextView = itemView.findViewById<TextView>(R.id.exclusion_item)
            deleteButton = itemView.findViewById<Button>(R.id.exclusion_button)
        }
    }

    override fun getItemId(position: Int): Long {
        return exclusions[position].phoneNumber.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val exclusionView = inflater.inflate(R.layout.exclusion_item, parent, false)
        return ViewHolder(exclusionView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentExclusion = exclusions[position]
        val textView = holder.phoneTextView
        textView.text = currentExclusion.phoneNumber.toString()
        holder.deleteButton.setOnClickListener {
            delete(currentExclusion)
        }
    }

    override fun getItemCount(): Int {
        return exclusions.size
    }
}