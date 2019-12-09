package com.example.encrypto

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.sql.ManageDB

import kotlinx.android.synthetic.main.activity_reset_pin.*
import kotlinx.android.synthetic.main.content_reset_pin.*

class ResetPIN : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pin)
        setSupportActionBar(toolbar)

        buttonReset.setOnClickListener{
            ManageDB().changePin(this, resetPIN.text.toString())
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

}
