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
import com.example.encrypto.classes.ManageDB
import kotlinx.android.synthetic.main.activity_show_account.*
import kotlinx.android.synthetic.main.content_show_account.*
import kotlinx.android.synthetic.main.dialog_confirm_pin.*
import kotlinx.android.synthetic.main.dialog_confirm_pin.view.*

class ShowAccountActivity : AppCompatActivity() {

    private var selection: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_account)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        selection = intent.getStringExtra("Account")!!
        et_show_account.text = selection

        et_show_username.text = ManageDB().getUsername(this, selection)

        button_show_password.setOnClickListener {
            val view = View.inflate(this, R.layout.dialog_confirm_pin, null)
            val builder = AlertDialog.Builder(this)
                .setView(view)
                .setTitle("Confrim Identity")
            val dialog = builder.show()
            view.button_confirmdialog_confirm.setOnClickListener {
                dialog.dismiss()
                showpass(view)
            }
            view.et_confirmdialog_pin.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                    button_confirmdialog_confirm.callOnClick()
                    return@OnKeyListener true
                }
                return@OnKeyListener false
            })
            view.button_confirmdialog_cancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        img_copy_username.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", et_show_username.text.toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(
                this,
                "Copied Username",
                Toast.LENGTH_SHORT
            ).show()
        }

        img_copy_password.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", et_show_password.text.toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(
                this,
                "Copied Password!",
                Toast.LENGTH_SHORT
            ).show()
        }

        button_delete_password.setOnClickListener {
            val view = View.inflate(this, R.layout.dialog_confirm_pin, null)
            val builder = AlertDialog.Builder(this)
                .setView(view)
                .setTitle("Confirm Identity")
            val dialog = builder.show()
            view.button_confirmdialog_confirm.setOnClickListener {
                dialog.dismiss()
                delacc(view)
            }
            view.et_confirmdialog_pin.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                    delacc(view)
                    dialog.dismiss()
                    return@OnKeyListener true
                }
                return@OnKeyListener false
            })
            view.button_confirmdialog_cancel.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    private fun showpass(view: View) {
        val pin = view.et_confirmdialog_pin.text.toString()
        if (ManageDB().checkPin(this, pin)) {
            et_show_password.text = ManageDB().getPassword(this, selection)
        } else {
            Toast.makeText(this, "Incorrect PIN!\nNo action taken", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun delacc(view: View) {
        val pin = view.et_confirmdialog_pin.text.toString()
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

