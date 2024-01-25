package com.example.core_utils

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log

class SharedPref(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "MyPrefs"
        private const val KEY_SELECTED_LANGUAGE = "selectedLanguage"
        private const val KEY_IS_SOUND_ENABLED = "isSoundEnabled"
        private const val KEY_NAME = "nameUser"
        private const val KEY_PHOTO_URI = "photoUri"

    }

    var selectedName:String?
        get() = sharedPreferences.getString(KEY_NAME, null)
        set(value) {
            sharedPreferences.edit().putString(KEY_NAME, value).apply()
        }

    var selectedLanguage: String?
        get() = sharedPreferences.getString(KEY_SELECTED_LANGUAGE, null)
        set(value) {
            sharedPreferences.edit().putString(KEY_SELECTED_LANGUAGE, value).apply()
        }

    var isSoundEnabled: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_SOUND_ENABLED, true)
        set(value) {
            sharedPreferences.edit().putBoolean(KEY_IS_SOUND_ENABLED, value).apply()
        }

    var photoUri: Uri?
        get() {
            val uriString = sharedPreferences.getString(KEY_PHOTO_URI, null)
            val parsedUri = if (uriString != null) Uri.parse(uriString) else null
            Log.d("ProfileFragment1234", "Received photoUri: $parsedUri")
            return parsedUri
        }
        set(value) {
            sharedPreferences.edit().putString(KEY_PHOTO_URI, value.toString()).apply()
            Log.d("ProfileFragment1234", "Saved photoUri to shared preferences: $value")
        }
    var isOnBoardingShowed:Boolean
        get() = sharedPreferences.getBoolean(Constants.SHOWED_KEY, false)
        set(value) = sharedPreferences.edit().putBoolean(Constants.SHOWED_KEY, value).apply()

    var accessToken: String?
        get() = sharedPreferences.getString(Constants.TOKEN_KEY, null)
        set(value) {
            Log.d("SharedPref", "Setting Access Token: $value")
            sharedPreferences.edit().putString(Constants.TOKEN_KEY, value).apply()
        }

    var refreshToken: String?
        get() = sharedPreferences.getString(Constants.REFRESH_TOKEN_KEY, null)
        set(value) {
            Log.d("SharedPref", "Setting Refresh Token: $value")
            sharedPreferences.edit().putString(Constants.REFRESH_TOKEN_KEY, value).apply()
        }

}


