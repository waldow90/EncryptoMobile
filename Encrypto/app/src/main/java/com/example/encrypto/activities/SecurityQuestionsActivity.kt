package com.example.encrypto.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.R
import com.example.encrypto.classes.ManageDB
import kotlinx.android.synthetic.main.activity_security_questions.*
import kotlinx.android.synthetic.main.content_security_questions.*

class SecurityQuestionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security_questions)
        setSupportActionBar(toolbar)

        et_check_q1.text = ManageDB().getSecurityQuestion(this, 2)
        et_check_q2.text = ManageDB().getSecurityQuestion(this, 3)
        et_check_q3.text = ManageDB().getSecurityQuestion(this, 4)

        var wrong = 0

        et_check_a3.setOnKeyListener(View.OnKeyListener{ _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                button_check_answers.callOnClick()
                return@OnKeyListener true
            }
            false
        })


        button_check_answers.setOnClickListener {
            when {
                et_check_a1.text.length < 2 || et_check_a1.text.length > 31 -> {
                    et_check_a1.setText("")
                    et_check_a1.setHintTextColor(Color.RED)
                    et_check_a1.hint = getString(R.string.answer_cannot_be_empty)
                }
                et_check_a2.text.length < 2 || et_check_a2.text.length > 31 -> {
                    et_check_a2.setText("")
                    et_check_a2.setHintTextColor(Color.RED)
                    et_check_a2.hint = getString(R.string.answer_cannot_be_empty)
                }
                et_check_a3.text.length < 2 || et_check_a3.text.length > 31 -> {
                    et_check_a3.setText("")
                    et_check_a3.setHintTextColor(Color.RED)
                    et_check_a3.hint = getString(R.string.answer_cannot_be_empty)
                }
                else -> {
                    if (
                        ManageDB().checkSecurityQuestion(this, et_check_a1.text.toString(), 2) &&
                        ManageDB().checkSecurityQuestion(this, et_check_a2.text.toString(), 3) &&
                        ManageDB().checkSecurityQuestion(this, et_check_a3.text.toString(), 4)
                    ) {
                        startActivity(Intent(this, ResetPINAcitivty::class.java))
                        finish()
                    } else {
                        wrong++
                        when (wrong) {
                            1 -> Toast.makeText(
                                this,
                                "Incorrect answers!\n2 tries left",
                                Toast.LENGTH_LONG
                            ).show()
                            2 -> Toast.makeText(
                                this,
                                "Incorrect answers!\n1 try left",
                                Toast.LENGTH_LONG
                            ).show()
                            3 -> {
                                Toast.makeText(
                                    this,
                                    "Incorrect answers!\nClosing app",
                                    Toast.LENGTH_LONG
                                ).show()
                                finish()
                            }
                        }
                    }
                }
            }
        }
    }
}
