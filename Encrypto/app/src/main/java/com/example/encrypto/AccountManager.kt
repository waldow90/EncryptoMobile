package com.example.encrypto

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.sql.ManageDB

import kotlinx.android.synthetic.main.activity_account_manager.*
import kotlinx.android.synthetic.main.content_account_manager.*

class AccountManager : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_manager)
        setSupportActionBar(toolbar)

        val acc = ManageDB().GetAccounts(this)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, acc)
        listAccounts.adapter = adapter

        listAccounts.setOnItemClickListener{ parent, _, position, _ ->
            val intent = Intent (this, ShowAccount::class.java)
            var selection = parent.getItemAtPosition(position).toString()
            intent.putExtra("Account", selection)
            startActivity(intent)
        }

        fab.setOnClickListener{
            startActivity(Intent(this, AddAccount::class.java))
        }
    }

    override fun onRestart() {
        super.onRestart()
        recreate()
    }

}
