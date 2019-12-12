package com.example.encrypto
<<<<<<< HEAD
import android.content.ClipboardManager
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.os.Bundle
import android.view.View
=======

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
>>>>>>> b10e0b2ba5ea1085f02744dd5f6fb98a508fee87
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.sql.ManageDB

import android.widget.Toast
import androidx.core.content.getSystemService
import kotlinx.android.synthetic.main.activity_show_account.*
import kotlinx.android.synthetic.main.content_show_account.*
<<<<<<< HEAD
import android.R.attr.label
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
//import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T





=======
import kotlinx.android.synthetic.main.dialog_confirm_pin.view.*
>>>>>>> b10e0b2ba5ea1085f02744dd5f6fb98a508fee87

class ShowAccount : AppCompatActivity() {

    private var selection: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_account)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

<<<<<<< HEAD
      //  val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val selection = intent.getStringExtra("Account")
=======
        selection = intent.getStringExtra("Account")
>>>>>>> b10e0b2ba5ea1085f02744dd5f6fb98a508fee87
        Account.text = selection


        Username.text = ManageDB().GetUsername(this, selection)

        showPass.setOnClickListener {
<<<<<<< HEAD
=======
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_confirm_pin, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Confrim Identity")
            val mAlertDialog = mBuilder.show()
            mDialogView.confirmdialog_buttonconfirm.setOnClickListener {
                mAlertDialog.dismiss()
                showpass(mDialogView)
            }
            mDialogView.confirmdialog_pin.setOnKeyListener(View.OnKeyListener{_, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
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

            btnDelete.setOnClickListener {
                val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_confirm_pin, null)
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
    }

    fun showpass(mDialogView: View) {
        val pin = mDialogView.confirmdialog_pin.text.toString()
        if (ManageDB().CheckPin(this, pin)) {
>>>>>>> b10e0b2ba5ea1085f02744dd5f6fb98a508fee87
            Password.text = ManageDB().GetPassword(this, selection)
        } else {
            Toast.makeText(this, "Incorrect PIN!\nNo action taken", Toast.LENGTH_LONG)
                .show()
        }
    }

<<<<<<< HEAD
        btnDelete.setOnClickListener {
=======
    fun delacc(mDialogView: View) {
        val pin = mDialogView.confirmdialog_pin.text.toString()
        if (ManageDB().CheckPin(this, pin)) {
>>>>>>> b10e0b2ba5ea1085f02744dd5f6fb98a508fee87
            ManageDB().DeleteAccount(this, selection)
            finish()
        } else {
            Toast.makeText(this, "Incorrect PIN!\nNo action taken", Toast.LENGTH_LONG)
                .show()
        }
        val gebruikers = Username.text

        PassCopy.setOnClickListener{
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", Password.text.toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(
                this,
                "text has been copied!",
                Toast.LENGTH_SHORT
            ).show()
        }
        gebcopy.setOnClickListener{
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", Username.text.toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(
                this,
                "text has been copied!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}

<<<<<<< HEAD

=======
>>>>>>> b10e0b2ba5ea1085f02744dd5f6fb98a508fee87
