package com.example.encrypto.classes

import android.app.AlertDialog
import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.preference.Preference
import com.example.encrypto.R
import kotlinx.android.synthetic.main.dialog_change_pin.view.*

class ChangePinPreference : Preference {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    override fun onClick() {
        onClickChangePin(context)
        super.onClick()
    }

    private fun onClickChangePin(context: Context) {
        val mDialogView = View.inflate(context, R.layout.dialog_change_pin, null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)
            .setTitle("Change PIN")
            .setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
            }
            .setPositiveButton("Confirm") { _, _ ->
                if (mDialogView.et_changedialog_oldpin.text.toString().length > 3 && mDialogView.et_changedialog_newpin.text.toString().length > 3) {
                    val oldpin = mDialogView.et_changedialog_oldpin.text.toString()
                    val newpin = mDialogView.et_changedialog_newpin.text.toString()
                    checkPin(oldpin, newpin, context)
                } else {
                    Toast.makeText(context, "Please enter a valid PIN", Toast.LENGTH_LONG).show()
                }
            }
        val mAlertDialog = mBuilder.show()
        mDialogView.et_changedialog_newpin.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                if (mDialogView.et_changedialog_oldpin.text.toString().length > 3 && mDialogView.et_changedialog_newpin.text.toString().length > 3) {
                    val oldpin = mDialogView.et_changedialog_oldpin.text.toString()
                    val newpin = mDialogView.et_changedialog_newpin.text.toString()
                    checkPin(oldpin, newpin, context)
                } else {
                    Toast.makeText(context, "Please enter a valid PIN", Toast.LENGTH_LONG).show()
                }
                mAlertDialog.dismiss()
                return@OnKeyListener true
            }
            return@OnKeyListener false
        })
    }

    private fun checkPin(newpin: String, oldpin: String, context: Context) {
        if (ManageDB().checkPin(context, oldpin)) {
            ManageDB().changePin(context, newpin)
            Toast.makeText(context, "PIN changed", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Incorrect PIN!\nNo action taken", Toast.LENGTH_LONG)
                .show()
        }
    }
}