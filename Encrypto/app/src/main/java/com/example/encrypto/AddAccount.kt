package com.example.encrypto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.sql.ManageDB

import kotlinx.android.synthetic.main.activity_add_account.*
import kotlinx.android.synthetic.main.content_add_account.*


class AddAccount : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_account)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab2.setOnClickListener{
            ManageDB().AddAccount(this, Account.text.toString(), Username.text.toString(), Password.text.toString())
            finish()
        }

        buttonGen.setOnClickListener{
            Password.setText(CustomPasswordGenerator().GeneratePassword(this))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
