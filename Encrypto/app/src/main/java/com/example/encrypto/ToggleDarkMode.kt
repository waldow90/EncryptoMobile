package com.example.encrypto

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.SwitchPreference
import com.example.encrypto.sql.Changepin

class ToggleDarkMode : SwitchPreference {

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

class ChangePin: Preference{

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onClick() {
        Changepin().onClickChangePin(context)
        super.onClick()
    }
}

