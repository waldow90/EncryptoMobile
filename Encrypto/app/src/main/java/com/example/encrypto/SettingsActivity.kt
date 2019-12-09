package com.example.encrypto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import android.view.MotionEvent
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import kotlinx.coroutines.delay

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

    override fun onUserInteraction() {
        super.onUserInteraction()
        val settingsprefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
        val theme = settingsprefs.getBoolean("settingsTheme", false)
        if(theme){setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)}
        else {setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)}
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

//    override fun onStop() {
//        super.onStop()
//        val settingsprefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
//        val theme = settingsprefs.getBoolean("settingsTheme", false)
//        if(theme){setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)}
//        else if (!theme) {setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)}
//    }
}