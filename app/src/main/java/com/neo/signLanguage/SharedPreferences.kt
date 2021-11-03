package com.neo.signLanguage

import androidx.appcompat.app.AppCompatActivity

class SharedPreferences (var activity: AppCompatActivity) {
    private val SETTINGS = "SETTINGS"
    private val DARK_THEME = "DARK_THEME"
    private val DELAY = "DELAY"

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
        return settings.getInt(DELAY, 250)
    }

    fun setDelay(state: Int) {
        val settings = activity.getSharedPreferences(SETTINGS, 0)
        val editor = settings.edit()
        editor.putInt(DELAY, state).apply()
    }




}