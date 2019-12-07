package com.example.encrypto

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.encrypto.sql.ManageDB

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        var wrong = 0

        buttonLogin.setOnClickListener {
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

        if (ManageDB().CheckFirstTime(this)) {
            ManageDB().AddDefault(this)
            startActivity(Intent(this, FirstLogin::class.java))
        }

        btnForgotpin.setOnClickListener {
            startActivity(Intent(this, SecurityQuestions::class.java))
            finish()
        }
    }


}
