package com.example.encrypto.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.R
import com.example.encrypto.classes.ManageDB

import kotlinx.android.synthetic.main.activity_reset_pin.*
import kotlinx.android.synthetic.main.content_reset_pin.*

class ResetPINAcitivty : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pin)
        setSupportActionBar(toolbar)

        button_reset_pin.setOnClickListener {
            if (et_reset_pin.text.length < 4) {
                Toast.makeText(this, "PIN should be between 4 and 8 characters", Toast.LENGTH_LONG)
                    .show()

            } else {
                ManageDB().changePin(this, et_reset_pin.text.toString())
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}
