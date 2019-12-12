package com.example.encrypto

<<<<<<< HEAD

import android.app.Activity
=======
>>>>>>> b10e0b2ba5ea1085f02744dd5f6fb98a508fee87
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
<<<<<<< HEAD

import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
=======
import android.widget.SearchView
import android.widget.Toast
>>>>>>> b10e0b2ba5ea1085f02744dd5f6fb98a508fee87
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.sql.ManageDB

import kotlinx.android.synthetic.main.activity_account_manager.*
import kotlinx.android.synthetic.main.content_account_manager.*
import kotlinx.android.synthetic.main.content_show_account.*

class AccountManager : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_manager)
        setSupportActionBar(toolbar)


        val acc = ManageDB().GetAccounts(this)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, acc)
        listAccounts.adapter = adapter

        listAccounts.setOnItemClickListener { parent, _, position, _ ->
            val intent = Intent(this, ShowAccount::class.java)
            var selection = parent.getItemAtPosition(position).toString()
            intent.putExtra("Account", selection)
            startActivity(intent)
        }

        fab.setOnClickListener {
            startActivity(Intent(this, AddAccount::class.java))
        }
    }

<<<<<<< HEAD



=======
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val item = menu.findItem(R.id.action_search)
        val searchview = item.actionView as SearchView

        searchview.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchview.clearFocus()
                searchview.setQuery("", false)
                item.collapseActionView()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val acc1 = ManageDB().GetAccounts(this@AccountManager, "%$newText%")
                val adapter = ArrayAdapter(this@AccountManager, android.R.layout.simple_list_item_1, acc1)
                listAccounts.adapter = adapter
                return false
            }

        })
        return true
    }
>>>>>>> b10e0b2ba5ea1085f02744dd5f6fb98a508fee87

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
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
