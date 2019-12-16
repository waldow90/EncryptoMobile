package com.example.encrypto.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.encrypto.R
import com.example.encrypto.classes.ManageDB
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class LoginActivity : AppCompatActivity() {

    private var wrong = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val settingsprefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
        val theme = settingsprefs.getBoolean("settingsTheme", false)
        if (theme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        if (ManageDB().checkFirstTime(this)) {
            startActivity(Intent(this, FirstLoginActivity::class.java))
        }

        et_login_pin.showKeyboard()

        et_login_pin.requestFocus()
        val t = Thread {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(et_login_pin, SHOW_IMPLICIT)
        }
        Handler().postDelayed(t, 100)

        button_login.setOnClickListener {
            login()
        }

        et_login_pin.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                button_login.callOnClick()
                return@OnKeyListener true
            }
            false
        })

        tv_forgot_pin.setOnClickListener {
            startActivity(Intent(this, SecurityQuestionsActivity::class.java))
            finish()
        }

        var click = 0
        loginlogo.setOnClickListener {
            click++
            if (click in 5..9) {
                loginlogo.visibility = ImageView.INVISIBLE
                imageView.visibility = ImageView.VISIBLE
            }
        }
        imageView.setOnClickListener {
            click++
            if (click >= 10) {
                loginlogo.visibility = ImageView.VISIBLE
                imageView.visibility = ImageView.INVISIBLE
                click = 0
            }
        }
    }

    private fun EditText.showKeyboard() {
        this.requestFocus()
        val t = Thread {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(this, SHOW_IMPLICIT)
        }
        Handler().postDelayed(t, 100)
    }

    private fun login() {
        if (ManageDB().checkPin(this, et_login_pin.text.toString())) {
            startActivity(Intent(this, AccountManagerActivity::class.java))
            finish()
        } else {
            wrong++
            when (wrong) {
                1 -> Toast.makeText(
                    this,
                    "Incorrect PIN!\n2 tries left",
                    Toast.LENGTH_LONG
                ).show()
                2 -> Toast.makeText(
                    this,
                    "Incorrect PIN!\n1 try left",
                    Toast.LENGTH_LONG
                ).show()
                3 -> {
                    Toast.makeText(
                        this,
                        "Incorrect PIN!\nPlease reset PIN",
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(Intent(this, SecurityQuestionsActivity::class.java))
                    finish()
                }
            }
        }
    }

}
