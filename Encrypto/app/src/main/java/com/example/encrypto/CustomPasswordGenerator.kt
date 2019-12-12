package com.example.encrypto

import android.content.Context
import android.util.Base64
import java.security.SecureRandom



class CustomPasswordGenerator {
    fun generatePassword(context: Context): String {
        val settings = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        val lenght = settings.getString("settingsPasslength", "24")?.toInt()
        val bytes = ByteArray(lenght!!)
        val random = SecureRandom()
        random.nextBytes(bytes)
        val encoder = Base64.encode(bytes, Base64.NO_PADDING)
        val pass = ByteArray(lenght)
        System.arraycopy(encoder, 0, pass, 0, lenght)
        return String(pass)

    }
}