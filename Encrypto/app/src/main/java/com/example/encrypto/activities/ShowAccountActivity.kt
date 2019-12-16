package com.example.encrypto.activities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.R
import com.example.encrypto.classes.ConfirmPinDialog
import com.example.encrypto.classes.ManageDB
import kotlinx.android.synthetic.main.activity_show_account.*
import kotlinx.android.synthetic.main.content_show_account.*

class ShowAccountActivity : AppCompatActivity(), ConfirmPinDialog.ConfirmPinDialogListener {

    private var selection: String = ""
    private var whatToDo = true //true if show password, false if delete account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_account)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        selection = intent.getStringExtra("Account")!!
        et_show_account.text = selection

        et_show_username.text = ManageDB().getUsername(this, selection)

        img_copy_password.visibility = View.INVISIBLE

        button_show_password.setOnClickListener {
            val dialog = ConfirmPinDialog()
            dialog.show(supportFragmentManager, "HELLO")
            whatToDo = true
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
                "Copied Username",
                Toast.LENGTH_SHORT
            ).show()
        }

        button_delete_password.setOnClickListener {
            val dialog = ConfirmPinDialog()
            dialog.show(supportFragmentManager, "HELLO")
            whatToDo = false
        }
    }

    override fun checkPin(pin: String) {
        if (ManageDB().checkPin(this, pin)) {
            if (whatToDo) {
                et_show_password.text = ManageDB().getPassword(this, selection)
                img_copy_password.visibility = View.VISIBLE
            } else {
                ManageDB().deleteAccount(this, selection)
            }
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

