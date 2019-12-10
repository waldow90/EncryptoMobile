package com.example.encrypto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.sql.ManageDB
import kotlinx.android.synthetic.main.activity_show_account.*
import kotlinx.android.synthetic.main.content_show_account.*

class ShowAccount : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_account)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val selection = intent.getStringExtra("Account")
        Account.text = selection!!

        Username.text = ManageDB().getUsername(this, selection)

        showPass.setOnClickListener{
            Password.text = ManageDB().getPassword(this, selection)
        }

        btnDelete.setOnClickListener{
            ManageDB().deleteAccount(this, selection)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
