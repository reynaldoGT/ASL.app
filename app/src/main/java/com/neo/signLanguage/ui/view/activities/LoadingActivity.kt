package com.neo.signLanguage.ui.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.gms.ads.MobileAds
import com.neo.signLanguage.R
import com.neo.signLanguage.utils.SharedPreferences

class LoadingActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    installSplashScreen()
/*    MobileAds.initialize(this)*/
    setContentView(R.layout.activity_loading)

    val intent: Intent = if (SharedPreferences.getIsFirstTime(this)) {
      Intent(this, WelcomeActivity::class.java)
    } else {
      Intent(this, MainActivity::class.java)
    }

    Handler().postDelayed({

      startActivity(intent)
      finish()
    }, 1000)
  }
}