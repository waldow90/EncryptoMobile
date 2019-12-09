package com.example.encrypto

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.*
import com.example.encrypto.sql.ManageDB
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val array = ByteArray(10)
        val array2 = ByteArray(20)
        d("Lock", array.toString())
        d("Lock", array2.toString())
        var wrong = 0
//        Dark.setOnClickListener { setDefaultNightMode(MODE_NIGHT_YES)}
//        Lights.setOnClickListener { setDefaultNightMode(MODE_NIGHT_NO) }
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

        txtForgotpin.setOnClickListener{
            startActivity(Intent(this, SecurityQuestions::class.java))
            finish()
        }

    }

}
