package com.example.encrypto.activities

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.R
import com.example.encrypto.classes.ManageDB
import kotlinx.android.synthetic.main.activity_first_login.*
import kotlinx.android.synthetic.main.content_first_login.*

class FirstLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_login)
        setSupportActionBar(toolbar)

        et_setup_pin.setOnKeyListener(View.OnKeyListener{ _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                button_setup.callOnClick()
                return@OnKeyListener true
            }
            false
        })

        button_setup.setOnClickListener {
            when {
                et_setup_pin.text.length < 4 || et_setup_pin.text.length > 31 -> {
                et_setup_pin.setText("")
                    et_setup_pin.setHintTextColor(Color.RED)
                    et_setup_pin.hint = getString(R.string.pin_length_warning)
            }
                et_setup_q1.text.length < 2 || et_setup_q1.text.length > 31 -> {
                    et_setup_q1.setText("")
                    et_setup_q1.setHintTextColor(Color.RED)
                    et_setup_q1.hint = getString(R.string.question_cannot_be_empty)
                }
                et_setup_a1.text.length < 2 || et_setup_a1.text.length > 31 -> {
                    et_setup_a1.setText("")
                    et_setup_a1.setHintTextColor(Color.RED)
                    et_setup_a1.hint = getString(R.string.answer_cannot_be_empty)
                }
                et_setup_q2.text.length < 2 || et_setup_q2.text.length > 31 -> {
                    et_setup_q2.setText("")
                    et_setup_q2.setHintTextColor(Color.RED)
                    et_setup_q2.hint = getString(R.string.question_cannot_be_empty)
                }
                et_setup_a2.text.length < 2 || et_setup_a2.text.length > 31 -> {
                    et_setup_a2.setText("")
                    et_setup_a2.setHintTextColor(Color.RED)
                    et_setup_a2.hint = getString(R.string.answer_cannot_be_empty)
                }
                et_setup_q3.text.length < 2 || et_setup_q3.text.length > 31 -> {
                    et_setup_q3.setText("")
                    et_setup_q3.setHintTextColor(Color.RED)
                    et_setup_q3.hint = getString(R.string.question_cannot_be_empty)
                }
                et_setup_a3.text.length < 2 || et_setup_a3.text.length > 31 -> {
                    et_setup_a3.setText("")
                    et_setup_a3.setHintTextColor(Color.RED)
                    et_setup_a3.hint = getString(R.string.answer_cannot_be_empty)
                }
                else -> {
                    ManageDB().addDefault(this)
                    ManageDB().setupPin(this, et_setup_pin.text.toString())
                    ManageDB()
                        .addSecurityQuestion(this, 2, et_setup_q1.text.toString(), et_setup_a1.text.toString())
                    ManageDB()
                        .addSecurityQuestion(this, 3, et_setup_q2.text.toString(), et_setup_a2.text.toString())
                    ManageDB()
                        .addSecurityQuestion(this, 4, et_setup_q3.text.toString(), et_setup_a3.text.toString())
                    finish()
                }
            }
        }
    }
}
