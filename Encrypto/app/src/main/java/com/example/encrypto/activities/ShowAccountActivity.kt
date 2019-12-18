package com.example.encrypto.activities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.R
import com.example.encrypto.classes.ConfirmPinDialog
import com.example.encrypto.classes.ManageDB
import kotlinx.android.synthetic.main.activity_show_account.*
import kotlinx.android.synthetic.main.content_show_account.*
import kotlinx.android.synthetic.main.dialog_confirm_pin.*

class ShowAccountActivity : AppCompatActivity(), ConfirmPinDialog.ConfirmPinDialogListener {

    private var selection: String = ""
    private var whatToDo = true //true if show password, false if delete account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_account)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //get account name from intent
        selection = intent.getStringExtra("Account")!!
        et_show_account.text = selection

        //get username from database
        et_show_username.text = ManageDB().getUsername(this, selection)

        //make button for copying password invisible
        img_copy_password.visibility = View.INVISIBLE

        button_show_password.setOnClickListener {
            //show dialog to confirm pin
            val dialog = ConfirmPinDialog()
            dialog.show(supportFragmentManager, "HELLO")
            whatToDo = true
        }

        img_copy_username.setOnClickListener {
            //copy username to clipboard
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
            //copy password to clipboard
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", et_show_password.text.toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(
                this,
                "Copied Username",
                Toast.LENGTH_SHORT
            ).show()
        }

        button_delete_password.setOnClickListener {
            //show dialog to confirm pin
            val dialog = ConfirmPinDialog()
            dialog.show(supportFragmentManager, "HELLO")
            whatToDo = false
        }
    }

    override fun checkPin(pin: String) {
        if (ManageDB().checkPin(this, pin)) {
            if (whatToDo) {
                //show password and button to copy password
                et_show_password.text = ManageDB().getPassword(this, selection)
                img_copy_password.visibility = View.VISIBLE
            } else {
                //delete account
                ManageDB().deleteAccount(this, selection)
            }
        } else {
            Toast.makeText(this, "Incorrect PIN!\nNo action taken", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun openKeyboard(etPin: EditText) {
        val t = Thread {
            etPin.requestFocus()
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(etPin, InputMethodManager.SHOW_IMPLICIT)
        }
        Handler().postDelayed(t, 200)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}

