package com.example.encrypto

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.classes.ManageDB

import kotlinx.android.synthetic.main.activity_first_login.*
import kotlinx.android.synthetic.main.content_first_login.*

class FirstLoginActivity : AppCompatActivity() {

    //TODO if wrong 3 times, remember when app opens again

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_login)
        setSupportActionBar(toolbar)

        btnSetup.setOnClickListener {
            when {
                PIN.text.length < 4 || PIN.text.length > 31 -> {
                PIN.setText("")
                    PIN.setHintTextColor(Color.RED)
                    PIN.hint = getString(R.string.pin_length_warning)
            }
                Q1.text.length < 2 || Q1.text.length > 31 -> {
                    Q1.setText("")
                    Q1.setHintTextColor(Color.RED)
                    Q1.hint = getString(R.string.question_cannot_be_empty)
                }
                A1.text.length < 2 || A1.text.length > 31 -> {
                    A1.setText("")
                    A1.setHintTextColor(Color.RED)
                    A1.hint = getString(R.string.answer_cannot_be_empty)
                }
                Q2.text.length < 2 || Q2.text.length > 31 -> {
                    Q2.setText("")
                    Q2.setHintTextColor(Color.RED)
                    Q2.hint = getString(R.string.question_cannot_be_empty)
                }
                A2.text.length < 2 || A2.text.length > 31 -> {
                    A2.setText("")
                    A2.setHintTextColor(Color.RED)
                    A2.hint = getString(R.string.answer_cannot_be_empty)
                }
                Q3.text.length < 2 || Q3.text.length > 31 -> {
                    Q3.setText("")
                    Q3.setHintTextColor(Color.RED)
                    Q3.hint = getString(R.string.question_cannot_be_empty)
                }
                A3.text.length < 2 || A3.text.length > 31 -> {
                    A3.setText("")
                    A3.setHintTextColor(Color.RED)
                    A3.hint = getString(R.string.answer_cannot_be_empty)
                }
                else -> {
                    ManageDB().addDefault(this)
                    ManageDB().changePin(this, PIN.text.toString())
                    ManageDB()
                        .addSecurityQuestion(this, 2, Q1.text.toString(), A1.text.toString())
                    ManageDB()
                        .addSecurityQuestion(this, 3, Q2.text.toString(), A2.text.toString())
                    ManageDB()
                        .addSecurityQuestion(this, 4, Q3.text.toString(), A3.text.toString())
                    finish()
                }
            }
        }
    }
}
