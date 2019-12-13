package com.example.encrypto

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.classes.ManageDB

import kotlinx.android.synthetic.main.activity_account_manager.*
import kotlinx.android.synthetic.main.content_account_manager.*

class AccountManagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_manager)
        setSupportActionBar(toolbar)

        val acc = ManageDB().getAccounts(this)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, acc)
        listAccounts.adapter = adapter

        listAccounts.setOnItemClickListener { parent, _, position, _ ->
            val intent = Intent(this, ShowAccountActivity::class.java)
            val selection = parent.getItemAtPosition(position).toString()
            intent.putExtra("Account", selection)
            startActivity(intent)
        }

        fab.setOnClickListener {
            startActivity(Intent(this, AddAccountActivity::class.java))
        }
    }

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
                val acc1 = ManageDB()
                    .getAccounts(this@AccountManagerActivity, "%$newText%")
                val adapter = ArrayAdapter(this@AccountManagerActivity, android.R.layout.simple_list_item_1, acc1)
                listAccounts.adapter = adapter
                return false
            }

        })
        return true
    }

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
