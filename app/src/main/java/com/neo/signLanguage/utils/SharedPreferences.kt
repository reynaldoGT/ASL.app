package com.neo.signLanguage.utils

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import com.neo.signLanguage.R

class SharedPreferences(var activity: AppCompatActivity) {
    private val SETTINGS =
        "${activity.applicationContext.resources.getString(R.string.app_name)} SHARED_PREFERENCES"
    private val DARK_THEME = "DARK_THEME"
    private val DELAY = "DELAY"
    private val COLOR = "COLOR"
    private val CURRENT_MESSAGE = "CURRENTMESSAGE"
    private val COUNT_AD_INTERTITIAL = "COUNT_AD_INTERTITIAL"
    private val minDelayTime = activity.applicationContext.resources.getInteger(R.integer.min_delay)
    private val maxDelayTime = activity.applicationContext.resources.getInteger(R.integer.max_delay)

    fun getTheme(): Boolean {
        val settings = activity.getSharedPreferences(SETTINGS, 0)
        return settings.getBoolean(DARK_THEME, false)
    }

    fun setTheme(state: Boolean) {

        val settings = activity.getSharedPreferences(SETTINGS, 0)
        val editor = settings.edit()
        editor.putBoolean(DARK_THEME, state).apply()
    }

    fun getDelay(): Int {
        val settings = activity.getSharedPreferences(SETTINGS, 0)
        return settings.getInt(DELAY, minDelayTime)
    }

    fun setDelay(state: Int) {
        if (state > maxDelayTime) {
            return
        }
        val settings = activity.getSharedPreferences(SETTINGS, 0)
        val editor = settings.edit()
        editor.putInt(DELAY, state).apply()
    }

    fun getColor(): Int {
        val settings = activity.getSharedPreferences(SETTINGS, 0)
        return settings.getInt(COLOR, 0)
    }

    fun setColor(state: Int) {
        val settings = activity.getSharedPreferences(SETTINGS, 0)
        val editor = settings.edit()
        editor.putInt(COLOR, state).apply()
    }

    fun getCurrentMessage(): String? {
        val settings = activity.getSharedPreferences(SETTINGS, 0)
        return settings.getString(CURRENT_MESSAGE, "")
    }

    fun setCurrentMessage(newState: String) {
        val settings = activity.getSharedPreferences(SETTINGS, 0)
        val editor = settings.edit()
        editor.putString(CURRENT_MESSAGE, newState).apply()
    }

    fun getAdCount(): Int {
        val settings = activity.getSharedPreferences(SETTINGS, 0)
        return settings.getInt(COUNT_AD_INTERTITIAL, 0)
    }

    fun resetAdCount() {
        val settings = activity.getSharedPreferences(SETTINGS, 0)
        val editor = settings.edit()
        editor.putInt(COUNT_AD_INTERTITIAL, 1).apply()
    }

    fun addInOneCounterAd() {
        val settings = activity.getSharedPreferences(SETTINGS, 0)
        val editor = settings.edit()
        val add = getAdCount() + 1
        editor.putInt(COUNT_AD_INTERTITIAL, add).apply()
    }


}