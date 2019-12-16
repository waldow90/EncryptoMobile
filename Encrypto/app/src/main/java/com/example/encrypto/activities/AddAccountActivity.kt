package com.example.encrypto.activities

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.R
import com.example.encrypto.classes.PasswordGenerator
import com.example.encrypto.classes.ManageDB

import kotlinx.android.synthetic.main.activity_add_account.*
import kotlinx.android.synthetic.main.content_add_account.*

class AddAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_account)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab_add_confirm.setOnClickListener{
            ManageDB()
                .addAccount(this, et_add_account.text.toString(), et_add_username.text.toString(), et_add_password.text.toString())
            finish()
        }

        et_add_password.setOnKeyListener(View.OnKeyListener{ _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                fab_add_confirm.callOnClick()
                return@OnKeyListener true
            }
            false
        })


        button_generate_password.setOnClickListener{
            et_add_password.setText(PasswordGenerator().generatePassword(this))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
