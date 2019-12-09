package com.example.encrypto

import android.app.UiModeManager.MODE_NIGHT_YES
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.preference.PreferenceFragmentCompat

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onStop() {
        super.onStop()
        /*Dark.setOnClickListener { AppCompatDelegate.MODE_NIGHT_YES }
        Light.setOnClickListener { AppCompatDelegate.MODE_NIGHT_NO }*/
        val settingsprefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
        val theme = settingsprefs.getBoolean("settingsTheme", false)
        if(theme){setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)}
        else if (!theme) {setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)}



    }
}