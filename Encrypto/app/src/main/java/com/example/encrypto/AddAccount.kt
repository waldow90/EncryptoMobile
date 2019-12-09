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
            ManageDB().addAccount(this, Account.text.toString(), Username.text.toString(), Password.text.toString())
            finish()
        }

        val settingspref = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)

        generatePassword(settingspref.getString("settingsTheme", "24")?.toInt())

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun generatePassword(length: Int?) {

    }
}
