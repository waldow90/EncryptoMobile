package com.example.encrypto


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main. activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

<<<<<<< HEAD


=======
>>>>>>> b10e0b2ba5ea1085f02744dd5f6fb98a508fee87
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
        val settingsprefs = PreferenceManager.getDefaultSharedPreferences(this)


        val theme = settingsprefs.getBoolean("settingsTheme", false)
        if (theme) {
            setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else if (!theme) {
            setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }


}