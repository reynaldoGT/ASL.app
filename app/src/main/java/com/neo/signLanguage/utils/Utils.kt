package com.neo.signLanguage.utils

import android.app.Activity
import com.google.android.material.snackbar.Snackbar
import com.neo.signLanguage.R
import com.neo.signLanguage.data.models.Sign
import com.orhanobut.logger.Logger
import java.text.Normalizer
import java.util.*


class Utils {
    companion object {
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

    }
}