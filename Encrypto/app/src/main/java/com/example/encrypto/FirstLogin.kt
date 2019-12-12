package com.example.encrypto

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.sql.ManageDB

import kotlinx.android.synthetic.main.activity_first_login.*
import kotlinx.android.synthetic.main.content_first_login.*

class FirstLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_login)
        setSupportActionBar(toolbar)

        btnSetup.setOnClickListener {
            if (PIN.text.length < 4) {
                Toast.makeText(this, "PIN should be between 4 and 8 characters", Toast.LENGTH_LONG)
                    .show()
            } else {

                ManageDB().ChangePin(this, PIN.text.toString())

                if (Q1.text.toString().isNullOrEmpty()) {
                    Toast.makeText(this, "Question 1 cannot be empty", Toast.LENGTH_LONG).show()
                } else if (A1.text.toString().isNullOrEmpty()) {
                    Toast.makeText(this, "Answer 1 cannot be empty", Toast.LENGTH_LONG).show()
                } else if (Q2.text.toString().isNullOrEmpty()) {
                    Toast.makeText(this, "Question 2 cannot be empty", Toast.LENGTH_LONG).show()
                } else if (A2.text.toString().isNullOrEmpty()) {
                    Toast.makeText(this, "Answer 2 cannot be empty", Toast.LENGTH_LONG).show()
                } else if (Q3.text.toString().isNullOrEmpty()) {
                    Toast.makeText(this, "Question 3  cannot be empty", Toast.LENGTH_LONG).show()
                } else if (A3.text.toString().isNullOrEmpty()) {
                    Toast.makeText(this, "Answer 3  cannot be empty", Toast.LENGTH_LONG).show()
                }
                else {
                    ManageDB().AddSecurityQuestion(this, 2, Q1.text.toString(), A1.text.toString())
                    ManageDB().AddSecurityQuestion(this, 3, Q2.text.toString(), A2.text.toString())
                    ManageDB().AddSecurityQuestion(this, 4, Q3.text.toString(), A3.text.toString())
                    finish()
                }

            }
        }
    }
}




