package com.example.encrypto.classes

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.SwitchPreference

class ThemePreference : SwitchPreference {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun onClick() {
        val settingsprefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        val theme = settingsprefs.getBoolean("settingsTheme", false)
        if (!theme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        super.onClick()
    }
}


