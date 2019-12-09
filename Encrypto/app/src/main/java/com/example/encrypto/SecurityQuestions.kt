package com.example.encrypto

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.sql.ManageDB

import kotlinx.android.synthetic.main.activity_security_questions.*
import kotlinx.android.synthetic.main.content_security_questions.*

class SecurityQuestions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security_questions)
        setSupportActionBar(toolbar)

        sqQ1.text = ManageDB().getSequrityQuestion(this, 2)
        sqQ2.text = ManageDB().getSequrityQuestion(this, 3)
        sqQ3.text = ManageDB().getSequrityQuestion(this, 4)

        btnMe.setOnClickListener{
            if
            (
                ManageDB().checkSequrityQuestion(this, checkA1.text.toString(), 2) &&
                ManageDB().checkSequrityQuestion(this, checkA2.text.toString(), 3) &&
                ManageDB().checkSequrityQuestion(this, checkA3.text.toString(), 4)
            )
            {
                startActivity(Intent(this, ResetPIN::class.java))
                finish()
            }
            else
            {
                Toast.makeText(this, "This is not you!", Toast.LENGTH_LONG).show()
            }
        }
    }

}
