package com.example.encrypto

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.sql.ManageDB

import kotlinx.android.synthetic.main.activity_show_account.*
import kotlinx.android.synthetic.main.content_show_account.*
import kotlinx.android.synthetic.main.dialog_confirm_pin.view.*

class ShowAccount : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_account)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val selection = intent.getStringExtra("Account")
        Account.text = selection

        Username.text = ManageDB().GetUsername(this, selection)

        showPass.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_confirm_pin, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Confrim Identity")
            val mAlertDialog = mBuilder.show()
            mDialogView.confirmdialog_buttonconfirm.setOnClickListener {
                mAlertDialog.dismiss()
                val pin = mDialogView.confirmdialog_pin.text.toString()
                if (ManageDB().CheckPin(this, pin)) {
                    Password.text = ManageDB().GetPassword(this, selection)
                } else {
                    Toast.makeText(this, "Incorrect PIN!\nNo action taken", Toast.LENGTH_LONG)
                        .show()
                }
            }
            mDialogView.confirmdialog_buttoncancel.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }

        btnDelete.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_confirm_pin, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Confirm Identity")
            val mAlertDialog = mBuilder.show()
            mDialogView.confirmdialog_buttonconfirm.setOnClickListener {
                mAlertDialog.dismiss()
                val pin = mDialogView.confirmdialog_pin.text.toString()
                if (ManageDB().CheckPin(this, pin)) {
                    ManageDB().DeleteAccount(this, selection)
                    finish()
                } else {
                    Toast.makeText(this, "Incorrect PIN!\nNo action taken", Toast.LENGTH_LONG)
                        .show()
                }
            }
            mDialogView.confirmdialog_buttoncancel.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}

