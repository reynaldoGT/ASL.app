package com.neo.signLanguage.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
import com.neo.signLanguage.data.models.Sign
import java.text.Normalizer
import java.util.*


class Utils {
    companion object {
        private val RECOGNIZE_SPEECH_ACTIVITY = 1

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
            val arraySentenceSing = ArrayList<Sign>()
            val stringArray = cleanedString.toCharArray()
            for (i in stringArray) {
                for (letterPosition in DataSign.getLetterArray()) {
                    if (letterPosition.letter == i.toString()) {
                        arraySentenceSing.add(letterPosition)
                    }
                }
            }
            return arraySentenceSing
        }

        private fun cleanString(string: String): String {
            val re = Regex("[^[A-Za-z0-9 ,ñÀ-ú]+\$]")
            var stringCleaned = ""
            stringCleaned = string.trim().lowercase()
            stringCleaned = re.replace(stringCleaned, "") // works
            stringCleaned = stringCleaned.replace("\\s+".toRegex(), " ")

            stringCleaned = Normalizer.normalize(stringCleaned, Normalizer.Form.NFD)
            return stringCleaned
        }

        fun vibratePhone(context: Context, timer: Long = 200) {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        timer,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(timer)
            }
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
    }

}