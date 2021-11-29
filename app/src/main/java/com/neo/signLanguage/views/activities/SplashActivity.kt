package com.neo.signLanguage.views.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.neo.signLanguage.provider.TabNavigatorActivity
import com.neo.signLanguage.utils.SharedPreferences


class SplashActivity : AppCompatActivity() {
    companion object {
        lateinit var pref: SharedPreferences

        fun getColorShared(context: Context): Int {
            return ContextCompat.getColor(
                context,
                pref.getColor()
            )
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, TabNavigatorActivity::class.java))
        pref = SharedPreferences(this)
        finish()
    }
}