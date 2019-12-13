package com.example.encrypto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.classes.PasswordGenerator
import com.example.encrypto.classes.ManageDB

import kotlinx.android.synthetic.main.activity_add_account.*
import kotlinx.android.synthetic.main.content_add_account.*

class AddAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_account)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab2.setOnClickListener{
            ManageDB()
                .addAccount(this, Account.text.toString(), Username.text.toString(), Password.text.toString())
            finish()
        }

        buttonGen.setOnClickListener{
            Password.setText(PasswordGenerator().generatePassword(this))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
