package com.example.core_utils

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LocalLangManager {
    private const val SELECTED_LANGUAGE_KEY = "selectedLanguage"

    fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)

        val displayMetrics = resources.displayMetrics
        resources.updateConfiguration(configuration, displayMetrics)

        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(SELECTED_LANGUAGE_KEY, languageCode)
        editor.apply()
    }

    fun getSelectedLanguage(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString(SELECTED_LANGUAGE_KEY, null)
    }
}