package com.example.encrypto.classes

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.encrypto.R


class ConfirmPinDialog : AppCompatDialogFragment() {

    private lateinit var listener: ConfirmPinDialogListener


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = View.inflate(activity, R.layout.dialog_confirm_pin, null)

        val etPin = view.findViewById<EditText>(R.id.et_confirmdialog_pin)
        builder.setView(view)
            .setTitle("Confirm identity")
            .setMessage("Enter PIN")
            .setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(activity, "Cancelled", Toast.LENGTH_LONG).show()
            }
            .setPositiveButton("Confirm") { _, _ ->
                if(etPin.text.toString().length > 3) {
                    val pin = etPin.text.toString()
                    listener.checkPin(pin)
                }else{
                    Toast.makeText(context, "Please enter a valid PIN", Toast.LENGTH_LONG).show()
                }
            }
        listener.openKeyboard(etPin)

        etPin.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val pin = etPin.text.toString()
                listener.checkPin(pin)
                dialog?.dismiss()
                return@OnKeyListener true
            }
            false
        })
        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as ConfirmPinDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + "must implement ConfirmPinDialogListener")
        }
    }

    interface ConfirmPinDialogListener {
        fun checkPin(pin: String) {

        }

        fun openKeyboard(etPin: EditText){

        }
    }

}