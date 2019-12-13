package com.example.encrypto.sql

import android.app.AlertDialog
import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.example.encrypto.R
import kotlinx.android.synthetic.main.dialog_change_pin.view.*
import kotlinx.android.synthetic.main.dialog_confirm_pin.view.*

class Changepin {
    fun onClickChangePin(context: Context) {
        val mDialogView = View.inflate(context, R.layout.dialog_change_pin, null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)
            .setTitle("Change PIN")
        val mAlertDialog = mBuilder.show()
        mDialogView.changedialog_buttonconfirm.setOnClickListener {
            mAlertDialog.dismiss()
            checkPin(mDialogView, context)
        }
        mDialogView.changedialog_newpin.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                checkPin(mDialogView, context)
                mAlertDialog.dismiss()
                return@OnKeyListener true
            }
            return@OnKeyListener false
        })
        mDialogView.changedialog_buttoncancel.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    private fun checkPin(view: View, context: Context) {
        val pin = view.changedialog_oldpin.text.toString()
        if (ManageDB().checkPin(context, pin)) {
            changePin(view, context)
        } else {
            Toast.makeText(context, "Incorrect PIN!\nNo action taken", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun changePin(view: View, context: Context) {
        Toast.makeText(context, "PIN changed", Toast.LENGTH_LONG).show()
        ManageDB().changePin(context, view.changedialog_newpin.text.toString())
    }
}