package com.example.encrypto

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.sql.ManageDB

import kotlinx.android.synthetic.main.activity_security_questions.*
import kotlinx.android.synthetic.main.content_security_questions.*

class SecurityQuestions : AppCompatActivity() {

    //TODO if wrong 3 times, close app

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security_questions)
        setSupportActionBar(toolbar)

        sqQ1.text = ManageDB().getSecurityQuestion(this, 2)
        sqQ2.text = ManageDB().getSecurityQuestion(this, 3)
        sqQ3.text = ManageDB().getSecurityQuestion(this, 4)

        var wrong = 0

        btnMe.setOnClickListener {
            when {
                checkA1.text.length < 2 || checkA1.text.length > 31 -> {
                    checkA1.setText("")
                    checkA1.setHintTextColor(Color.RED)
                    checkA1.hint = getString(R.string.answer_cannot_be_empty)
                }
                checkA2.text.length < 2 || checkA2.text.length > 31 -> {
                    checkA2.setText("")
                    checkA2.setHintTextColor(Color.RED)
                    checkA2.hint = getString(R.string.answer_cannot_be_empty)
                }
                checkA3.text.length < 2 || checkA3.text.length > 31 -> {
                    checkA3.setText("")
                    checkA3.setHintTextColor(Color.RED)
                    checkA3.hint = getString(R.string.answer_cannot_be_empty)
                }
                else -> {
                    if (
                        ManageDB().checkSecurityQuestion(this, checkA1.text.toString(), 2) &&
                        ManageDB().checkSecurityQuestion(this, checkA2.text.toString(), 3) &&
                        ManageDB().checkSecurityQuestion(this, checkA3.text.toString(), 4)
                    ) {
                        startActivity(Intent(this, ResetPIN::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "This is not you!", Toast.LENGTH_LONG).show()
                        wrong++
                    }
                }
            }
        }
    }
}
