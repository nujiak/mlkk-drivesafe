package com.mylongkenkai.drivesafe.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.mylongkenkai.drivesafe.R

class InputDialog : androidx.fragment.app.DialogFragment() {

    private lateinit var listener: InputDialogListener

    interface InputDialogListener {
        fun onDialogPositiveClick(dialogFragment: DialogFragment)
        fun onDialogNegativeClick(dialogFragment: DialogFragment)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialog: AlertDialog = activity?.let {
            val builder = AlertDialog.Builder(it).setTitle(R.string.exclusion_dialog_title)
                .setView(requireActivity().layoutInflater.inflate(R.layout.dialog_input, null))
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    listener.onDialogPositiveClick(this)
                }
                .setNegativeButton(android.R.string.cancel) { _, _ ->
                    listener.onDialogPositiveClick(this) // User cancelled the dialog
                }

            // Create the AlertDialog
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
        return alertDialog
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the InputDialogListener so we can send events to the host
            listener = context as InputDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement InputDialogListener"))
        }
    }
}