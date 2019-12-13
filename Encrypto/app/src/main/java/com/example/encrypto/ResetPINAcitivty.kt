package com.example.encrypto

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.classes.ManageDB

import kotlinx.android.synthetic.main.activity_reset_pin.*
import kotlinx.android.synthetic.main.content_reset_pin.*

class ResetPINAcitivty : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pin)
        setSupportActionBar(toolbar)

        buttonReset.setOnClickListener {
            if (resetPIN.text.length < 4) {
                Toast.makeText(this, "PIN should be between 4 and 8 characters", Toast.LENGTH_LONG)
                    .show()

            } else {
                ManageDB().changePin(this, resetPIN.text.toString())
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}
