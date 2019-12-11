package com.example.encrypto
import android.content.ClipboardManager
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.sql.ManageDB

import android.widget.Toast
import androidx.core.content.getSystemService
import kotlinx.android.synthetic.main.activity_show_account.*
import kotlinx.android.synthetic.main.content_show_account.*
import android.R.attr.label
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
//import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T






class ShowAccount : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_account)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

      //  val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val selection = intent.getStringExtra("Account")
        Account.text = selection


        Username.text = ManageDB().GetUsername(this, selection)

        showPass.setOnClickListener {
            Password.text = ManageDB().GetPassword(this, selection)
        }

        btnDelete.setOnClickListener {
            ManageDB().DeleteAccount(this, selection)
            finish()
        }
        val gebruikers = Username.text

        PassCopy.setOnClickListener{
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", Password.text.toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(
                this,
                "text has been copied!",
                Toast.LENGTH_SHORT
            ).show()
        }
        gebcopy.setOnClickListener{
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", Username.text.toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(
                this,
                "text has been copied!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}


