package com.neo.signLanguage.utils

import android.app.Activity
import com.google.android.material.snackbar.Snackbar
import com.neo.signLanguage.R
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

    }
}