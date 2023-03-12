package com.neo.signLanguage.utils

import android.content.Context
import androidx.core.content.ContextCompat

object SharedPreferences {
  private val SETTINGS = "SHARED_PREFERENCES"
  private val DARK_THEME = "DARK_THEME"
  private val DELAY = "DELAY"
  private val HAND_COLOR = "HAND_COLOR"
  private val CURRENT_MESSAGE = "CURRENTMESSAGE"
  private val COUNT_AD_INTERTITIAL = "COUNT_AD_INTERTITIAL"
  private val SHOW_TRANSITIONS = "SHOW_TRANSITIONS"
  private val SELECTED_TRANSITION = "SELECTED_TRANSITION"
  private val VIBRATION = "VIBRATION"
  private val MEMORY_GAME_RECORD_EASY = "MEMORY_GAME_RECORD_EASY"
  private val MEMORY_GAME_RECORD_MEDIUM = "MEMORY_GAME_RECORD_MEDIUM"
  private val MEMORY_GAME_RECORD_HARD = "MEMORY_GAME_RECORD_HARD"
  private val IS_GRID_LAYOUT = "IS_GRID_LAYOUT"
  private val GUESS_GAME_THE_WORD_RECORD = "GUESS_GAME_THE_WORD_RECORD"
  /*private val minDelayTime = activity.applicationContext.resources.getInteger(R.integer.min_delay)
  private val maxDelayTime = activity.applicationContext.resources.getInteger(R.integer.max_delay)*/
  private val minDelayTime = 250
  private val maxDelayTime = 2500

  fun isDarkTheme(context: Context): Boolean {
    val settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    return settings.getBoolean(DARK_THEME, false)
  }

  fun setTheme(context: Context, state: Boolean) {
    val settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    val editor = settings.edit()
    editor.putBoolean(DARK_THEME, state).apply()
  }

  fun getDelay(context: Context): Int {
    val settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    return settings.getInt(DELAY, maxDelayTime / 2)
  }

  fun setDelay(context: Context, state: Int) {
    if (state > maxDelayTime) {
      return
    }
    val settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    val editor = settings.edit()
    editor.putInt(DELAY, state).apply()
  }

  fun getSharedPreferencesHandColor(context: Context): Int {
    val settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    return settings.getInt(
      HAND_COLOR,
      DataSign.getColorsList(context.applicationContext)[0].colorValue
    )
  }

  fun setHandColor(context: Context, state: Int) {
    val settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    val editor = settings.edit()
    editor.putInt(HAND_COLOR, state).apply()
  }

  fun getCurrentMessage(context: Context): String {
    val settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    return settings.getString(CURRENT_MESSAGE, "").toString()
  }

  fun setCurrentMessage(context: Context, newState: String) {
    val settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    val editor = settings.edit()
    editor.putString(CURRENT_MESSAGE, newState).apply()
  }

  fun getAdCount(context: Context): Int {
    val settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    return settings.getInt(COUNT_AD_INTERTITIAL, 0)
  }

  fun resetAdCount(context: Context) {
    val settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    val editor = settings.edit()
    editor.putInt(COUNT_AD_INTERTITIAL, 1).apply()
  }

  fun addInOneCounterAd(context: Context) {
    val settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    val editor = settings.edit()
    val add = getAdCount(context) + 1
    editor.putInt(COUNT_AD_INTERTITIAL, add).apply()
  }

  fun getShowTransition(context: Context): Boolean {
    val settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    return settings.getBoolean(SHOW_TRANSITIONS, false)
  }

  fun setShowTransition(context: Context, newState: Boolean) {
    val settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    val editor = settings.edit()
    editor.putBoolean(SHOW_TRANSITIONS, newState).apply()
  }

  fun getVibration(context: Context): Boolean {
    val settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    return settings.getBoolean(VIBRATION, false)
  }

  fun setVibration(context: Context, newState: Boolean) {
    val settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    val editor = settings.edit()
    editor.putBoolean(VIBRATION, newState).apply()
  }

  fun getSelectedTransition(context: Context): Int {
    val settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    return settings.getInt(SELECTED_TRANSITION, 0)
  }

  fun setSelectedTransition(context: Context, newState: Int) {
    val settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    val editor = settings.edit()
    editor.putInt(SELECTED_TRANSITION, newState).apply()
  }

  fun getIsGridLayout(context: Context): Boolean {
    val settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    return settings.getBoolean(IS_GRID_LAYOUT, false)
  }

  fun setIsGridLayout(context: Context, newState: Boolean) {
    val settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    val editor = settings.edit()
    editor.putBoolean(IS_GRID_LAYOUT, newState).apply()
  }

  fun getMemoryRecord(context: Context, type: String): Int {
    val settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
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

  fun setMemoryRecord(context: Context, type: String, newState: Int) {
    val settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
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

  fun getColorShared(context: Context): Int {
    return ContextCompat.getColor(
      context,
      getSharedPreferencesHandColor(context)
    )
  }

  fun getGuessGameTheWordRecord(context: Context): Int {
    val settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    return settings.getInt(GUESS_GAME_THE_WORD_RECORD, 0)
  }

  fun setGetGuessGameTheWordRecord(context: Context, newState: Int) {
    val settings = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    val editor = settings.edit()
    editor.putInt(GUESS_GAME_THE_WORD_RECORD, newState).apply()
  }
}