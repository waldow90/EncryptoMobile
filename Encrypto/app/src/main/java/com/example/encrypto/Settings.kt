package com.example.encrypto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.encrypto.sql.ManageDB
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.content_settings.*

class Settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        switch1.setOnCheckedChangeListener { _, isChecked ->
            val value = if (isChecked) "Dark" else "Light"
            ManageDB().SetSetting(this, "Theme", value)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
