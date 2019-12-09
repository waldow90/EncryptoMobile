package com.example.encrypto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.sql.ManageDB
import kotlinx.android.synthetic.main.activity_first_login.*
import kotlinx.android.synthetic.main.content_first_login.*

class FirstLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_login)
        setSupportActionBar(toolbar)

        btnSetup.setOnClickListener{
            ManageDB().ChangePin(this, PIN.text.toString())
            ManageDB().AddSecurityQuestion(this, 2, Q1.text.toString(), A1.text.toString())
            ManageDB().AddSecurityQuestion(this, 3, Q2.text.toString(), A2.text.toString())
            ManageDB().AddSecurityQuestion(this, 4, Q3.text.toString(), A3.text.toString())
            finishActivity(0)
        }
    }
}
