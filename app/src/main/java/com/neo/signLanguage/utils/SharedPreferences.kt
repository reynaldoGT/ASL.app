package com.neo.signLanguage.utils

import androidx.appcompat.app.AppCompatActivity
import com.neo.signLanguage.R

class SharedPreferences(var activity: AppCompatActivity) {
  private val SETTINGS = "SHARED_PREFERENCES"
  private val DARK_THEME = "DARK_THEME"
  private val DELAY = "DELAY"
  private val COLOR = "COLOR"
  private val CURRENT_MESSAGE = "CURRENTMESSAGE"
  private val COUNT_AD_INTERTITIAL = "COUNT_AD_INTERTITIAL"
  private val SHOW_TRANSITIONS = "SHOW_TRANSITIONS"
  private val SELECTED_TRANSITION = "SELECTED_TRANSITION"
  private val VIBRATION = "VIBRATION"
  private val MEMORY_GAME_RECORD_EASY = "MEMORY_GAME_RECORD_EASY"
  private val MEMORY_GAME_RECORD_MEDIUM = "MEMORY_GAME_RECORD_MEDIUM"
  private val MEMORY_GAME_RECORD_HARD = "MEMORY_GAME_RECORD_HARD"
  private val GRID_LAYOUT = "GRID_LAYOUT"
  private val minDelayTime = activity.applicationContext.resources.getInteger(R.integer.min_delay)
  private val maxDelayTime = activity.applicationContext.resources.getInteger(R.integer.max_delay)
  private val vibration = true

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
    return settings.getInt(DELAY, maxDelayTime / 2)
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
    return settings.getInt(
      COLOR,
      DataSign.getColorsList(activity.applicationContext)[0].colorValue
    )
  }

  fun setColor(state: Int) {
    val settings = activity.getSharedPreferences(SETTINGS, 0)
    val editor = settings.edit()
    editor.putInt(COLOR, state).apply()
  }

  fun getCurrentMessage(): String {
    val settings = activity.getSharedPreferences(SETTINGS, 0)
    return settings.getString(CURRENT_MESSAGE, "").toString()
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

  fun getShowTransition(): Boolean {
    val settings = activity.getSharedPreferences(SETTINGS, -1)
    return settings.getBoolean(SHOW_TRANSITIONS, false)
  }

  fun setShowTransition(newState: Boolean) {
    val settings = activity.getSharedPreferences(SETTINGS, 0)
    val editor = settings.edit()
    editor.putBoolean(SHOW_TRANSITIONS, newState).apply()
  }

  fun getVibration(): Boolean {
    val settings = activity.getSharedPreferences(SETTINGS, -1)
    return settings.getBoolean(VIBRATION, false)
  }

  fun setVibration(newState: Boolean) {
    val settings = activity.getSharedPreferences(SETTINGS, 0)
    val editor = settings.edit()
    editor.putBoolean(VIBRATION, newState).apply()
  }

  fun getSelectedTransition(): Int {
    val settings = activity.getSharedPreferences(SETTINGS, 0)
    return settings.getInt(SELECTED_TRANSITION, 0)
  }

  fun setSelectedTransition(newState: Int) {
    val settings = activity.getSharedPreferences(SETTINGS, 0)
    val editor = settings.edit()
    editor.putInt(SELECTED_TRANSITION, newState).apply()
  }

  fun getIsGridLayout(): Boolean {
    val settings = activity.getSharedPreferences(SETTINGS, 0)
    return settings.getBoolean(GRID_LAYOUT, false)
  }

  fun setIsGridLayout(newState: Boolean) {
    val settings = activity.getSharedPreferences(SETTINGS, 0)
    val editor = settings.edit()
    editor.putBoolean(GRID_LAYOUT, newState).apply()
  }

  fun getMemoryRecord(type: String): Int {
    val settings = activity.getSharedPreferences(SETTINGS, 0)
    when (type) {
      "easy" -> {
        return settings.getInt(MEMORY_GAME_RECORD_EASY, 0)
      }
      "medium" -> {
        return settings.getInt(MEMORY_GAME_RECORD_MEDIUM, 0)
      }
      "hard" -> {
        return settings.getInt(MEMORY_GAME_RECORD_HARD, 0)
      }
    }
    return 0
  }

  fun setMemoryRecord(type: String, newState: Int) {
    val settings = activity.getSharedPreferences(SETTINGS, 0)
    val editor = settings.edit()

    when (type) {
      "easy" -> {
        editor.putInt(MEMORY_GAME_RECORD_EASY, newState).apply()
      }
      "medium" -> {
        editor.putInt(MEMORY_GAME_RECORD_MEDIUM, newState).apply()
      }
      "hard" -> {
        editor.putInt(MEMORY_GAME_RECORD_HARD, newState).apply()
      }
    }
  }

}