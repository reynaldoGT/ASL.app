package com.neo.signLanguage.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.neo.signLanguage.data.models.Sign
import com.neo.signLanguage.ui.view.activities.games.Difficulty
import com.neo.signLanguage.utils.SharedPreferences.getColorShared
import com.neo.signLanguage.utils.SharedPreferences.getPlaySoundInGames
import com.neo.signLanguage.utils.SharedPreferences.getSharedPreferencesHandColor
import com.neo.signLanguage.utils.SharedPreferences.getVibration
import java.text.Normalizer
import java.util.*


class Utils {
  companion object {
    fun getStringByIdName(context: Context, idName: String): String {
      val res: Resources = context.resources
      return res.getString(res.getIdentifier(idName, "string", context.packageName))
    }

    fun getLoLanguageTag(): String {
      return (Locale.getDefault().toLanguageTag())
    }

    fun showSnackBar(activity: Activity, resourcesMessage: Int) {
      Snackbar.make(
        activity.findViewById(android.R.id.content),
        resourcesMessage, Snackbar.LENGTH_LONG
      ).show()
    }

    fun messageToImages(message: String): ArrayList<Sign> {
      val cleanedString = cleanString(message)
      val lettersMap = DataSign.getLetterArray().associateBy { it.letter.first() }

      return cleanedString
        .mapNotNull { lettersMap[it] }
        .toCollection(arrayListOf())
    }

    fun cleanString(string: String): String {
      val re = Regex("[^[A-Za-z0-9 ,À-ú]+\$]")
      var stringCleaned = ""
      stringCleaned = string.trim().lowercase()
      stringCleaned = re.replace(stringCleaned, "") // works
      stringCleaned = stringCleaned.replace("\\s+".toRegex(), " ")

      stringCleaned = Normalizer.normalize(stringCleaned, Normalizer.Form.NFD)
      return stringCleaned
    }

    fun vibratePhone(context: Context, timer: Long = 200) {
      if (!getVibration(context)) return
      val vibrator = ContextCompat.getSystemService(context, Vibrator::class.java)
      if (Build.VERSION.SDK_INT >= 26) {
        vibrator?.vibrate(
          VibrationEffect.createOneShot(
            timer,
            VibrationEffect.DEFAULT_AMPLITUDE
          )
        )
      } else {
        vibrator?.vibrate(timer)
      }
    }

    fun playCorrectSound(context: Context, mediaPlayer: MediaPlayer) {
      if (!getPlaySoundInGames(context)) return
      mediaPlayer.start()
    }

    fun hideKeyboard(activity: Activity) {
      val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
      //Find the currently focused view, so we can grab the correct window token from it.
      var view = activity.currentFocus
      //If no view currently has focus, create a new one, just so we can grab a window token from it
      if (view == null) {
        view = View(activity)
      }
      imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    fun showSnackBarToGames(message: String, color: Int, view: View, context: Context) {
      Snackbar
        .make(
          view,
          message,
          Snackbar.LENGTH_SHORT,
        )
        .setBackgroundTint(
          ContextCompat.getColor(
            context,
            color
          )
        )
        .setTextColor(Color.WHITE)
        .show()
    }

    fun getHandColor(context: Context): ColorFilter {
      return ColorFilter.tint(
        androidx.compose.ui.graphics.Color(
          ContextCompat.getColor(
            context,
            getSharedPreferencesHandColor(context)
          )
        )
      )
    }

    fun getHandCurrentColor(context: Context): ComposeColor {
      return ComposeColor(
        ContextCompat.getColor(
          context,
          getSharedPreferencesHandColor(context)
        )
      )
    }

    fun setColorByDifficulty(difficulty: Difficulty): ComposeColor {
      return when (difficulty) {
        Difficulty.EASY -> ComposeColor(0xFF47C87C) // Verde claro
        Difficulty.MEDIUM -> ComposeColor(0xFFFFC800) // Amarillo
        Difficulty.HARD -> ComposeColor(0xFFF56342) // Naranja
        Difficulty.VERY_HARD -> ComposeColor(0xFFD9483B) // Rojo oscuro
        // Color primario de Material Design
      }
    }
  }
}