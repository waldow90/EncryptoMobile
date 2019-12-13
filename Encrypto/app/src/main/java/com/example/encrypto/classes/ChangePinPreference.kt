package com.example.encrypto.classes

import android.content.Context
import android.util.AttributeSet
import androidx.preference.Preference

class ChangePinPreference: Preference {

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onClick() {
        Changepin().onClickChangePin(context)
        super.onClick()
    }
}
