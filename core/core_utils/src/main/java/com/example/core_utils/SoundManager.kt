package com.example.core_utils

import android.content.Context
import android.media.MediaPlayer

class SoundManager private constructor(context: Context){

    private val buttonSound : MediaPlayer = MediaPlayer.create(context, R.raw.button)
    private val settingButton : MediaPlayer = MediaPlayer.create(context, R.raw.setting)

    private var isSoundEnable : Boolean = true

    companion object {
        private var instance : SoundManager? = null

        fun getInstance(context: Context):SoundManager {
            if(instance == null) {
                instance = SoundManager(context)
            }
            return instance!!
        }
    }

    fun enableSound() {
        isSoundEnable = true
    }

    fun disableSound() {
        isSoundEnable = false
    }
    fun playButtonSetting() {
        try {
            if (isSoundEnable) {
                settingButton.start()
            }
        } catch (e: IllegalStateException) {
            // Обработка ошибки воспроизведения звука
            e.printStackTrace()
        }
    }

    fun playButton() {
        try {
            if (isSoundEnable) {
                buttonSound.start()
            }
        } catch (e: IllegalStateException) {
            // Обработка ошибки воспроизведения звука
            e.printStackTrace()
        }
    }
}