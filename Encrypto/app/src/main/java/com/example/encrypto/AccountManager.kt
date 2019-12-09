package com.example.encrypto

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.sql.ManageDB

import kotlinx.android.synthetic.main.activity_account_manager.*
import kotlinx.android.synthetic.main.content_account_manager.*

class AccountManager : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_manager)
        setSupportActionBar(toolbar)

        val acc = ManageDB().getAccounts(this)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRestart() {
        super.onRestart()
        recreate()
    }

}
