package com.example.encrypto.classes

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import android.R.attr.password
import android.text.method.PasswordTransformationMethod


class ConfirmPinDialog : AppCompatDialogFragment() {

    private lateinit var listener: ConfirmPinDialogListener


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
//        val view = View.inflate(activity, R.layout.dialog_confirm_pin, null)
        val input = EditText(activity)
        input.hint = "PIN"
        input.inputType = InputType.TYPE_NUMBER_VARIATION_PASSWORD
        input.transformationMethod = PasswordTransformationMethod()
        input.isSingleLine = true
        val para = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        //val etPin = view?.findViewById<EditText>(com.example.encrypto.R.id.et_confirmdialog_pin)
        input.layoutParams = para
        builder.setView(input)
            .setTitle("Confirm identity")
            .setMessage("Enter PIN")
            .setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(activity, "Cancelled", Toast.LENGTH_LONG).show()
            }
            .setPositiveButton("Confirm") { _, _ ->
                val pin = input.text.toString()
                listener.checkPin(pin)
            }
        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as ConfirmPinDialogListener
        }catch(e: ClassCastException){
            throw ClassCastException(context.toString() + "must implement EcampleDialogListener")
        }
    }

    interface ConfirmPinDialogListener {
        fun checkPin(pin: String) {

        }
    }
}