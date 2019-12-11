package com.example.encrypto

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isInvisible
import com.example.encrypto.sql.ManageDB
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

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

        buttonLogin.setOnClickListener {
            login()
        }

        loginPass.setOnKeyListener(View.OnKeyListener{_, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                login()
                return@OnKeyListener true
            }
            false
        })

        if (ManageDB().CheckFirstTime(this)) {
            ManageDB().AddDefault(this)
            startActivity(Intent(this, FirstLogin::class.java))
        }

        txtForgotpin.setOnClickListener{
            startActivity(Intent(this, SecurityQuestions::class.java))
            finish()
        }

        var click = 0
        loginlogo.setOnClickListener{
            click++
            if (click in 5..9){
                loginlogo.visibility = ImageView.INVISIBLE
                imageView.visibility = ImageView.VISIBLE
            }
        }
        imageView.setOnClickListener{
            click++
            if (click >= 10){
                loginlogo.visibility = ImageView.VISIBLE
                imageView.visibility = ImageView.INVISIBLE
                click = 0
            }
        }
    }

    fun login(){
        if (ManageDB().CheckPin(this, loginPass.text.toString())) {
            startActivity(Intent(this, AccountManager::class.java))
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
                    startActivity(Intent(this, SecurityQuestions::class.java))
                    finish()
                }
            }
        }
    }

}
