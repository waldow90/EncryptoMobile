package com.example.encrypto

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.sql.ManageDB
import kotlinx.android.synthetic.main.activity_show_account.*
import kotlinx.android.synthetic.main.content_show_account.*
import kotlinx.android.synthetic.main.dialog_confirm_pin.view.*

class ShowAccount : AppCompatActivity() {

    private var selection: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_account)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        selection = intent.getStringExtra("Account")!!
        Account.text = selection

        Username.text = ManageDB().getUsername(this, selection)

        showPass.setOnClickListener {
            val mDialogView = View.inflate(this, R.layout.dialog_confirm_pin, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Confrim Identity")
            val mAlertDialog = mBuilder.show()
            mDialogView.confirmdialog_buttonconfirm.setOnClickListener {
                mAlertDialog.dismiss()
                showpass(mDialogView)
            }
            mDialogView.confirmdialog_pin.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                    showpass(mDialogView)
                    mAlertDialog.dismiss()
                    return@OnKeyListener true
                }
                return@OnKeyListener false
            })
            mDialogView.confirmdialog_buttoncancel.setOnClickListener {
                mAlertDialog.dismiss()
            }

            copyUser.setOnClickListener {
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("label", Username.text.toString())
                clipboard.setPrimaryClip(clip)
                Toast.makeText(
                    this,
                    "Copied Username",
                    Toast.LENGTH_SHORT
                ).show()

            }

            copyPass.setOnClickListener {
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("label", Password.text.toString())
                clipboard.setPrimaryClip(clip)
                Toast.makeText(
                    this,
                    "Copied Password!",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }

            btnDelete.setOnClickListener {
                val mDialogView = View.inflate(this, R.layout.dialog_confirm_pin, null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(mDialogView)
                    .setTitle("Confirm Identity")
                val mAlertDialog = mBuilder.show()
                mDialogView.confirmdialog_buttonconfirm.setOnClickListener {
                    mAlertDialog.dismiss()
                    delacc(mDialogView)
                }
                mDialogView.confirmdialog_pin.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                        delacc(mDialogView)
                        mAlertDialog.dismiss()
                        return@OnKeyListener true
                    }
                    return@OnKeyListener false
                })
                mDialogView.confirmdialog_buttoncancel.setOnClickListener {
                    mAlertDialog.dismiss()
                }
            }

    }

    private fun showpass(mDialogView: View) {
        val pin = mDialogView.confirmdialog_pin.text.toString()
        if (ManageDB().checkPin(this, pin)) {
            Password.text = ManageDB().getPassword(this, selection)
        } else {
            Toast.makeText(this, "Incorrect PIN!\nNo action taken", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun delacc(mDialogView: View) {
        val pin = mDialogView.confirmdialog_pin.text.toString()
        if (ManageDB().checkPin(this, pin)) {
            ManageDB().deleteAccount(this, selection)
            finish()
        } else {
            Toast.makeText(this, "Incorrect PIN!\nNo action taken", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}

