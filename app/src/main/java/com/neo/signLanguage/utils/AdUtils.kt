package com.neo.signLanguage.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.neo.signLanguage.ui.view.activities.MainActivity.Companion.sharedPrefs
import com.neo.signLanguage.utils.Utils.Companion.getStringByIdName
import com.orhanobut.logger.Logger
import java.util.*

class AdUtils {
  companion object {
    private var interstitial: InterstitialAd? = null
    fun initAds(context: Context) {
      val adRequest = AdRequest.Builder().build()
      InterstitialAd.load(
        context,
        getStringByIdName(context, "test_interstitial_id"),
        adRequest,
        object : InterstitialAdLoadCallback() {
          override fun onAdLoaded(interstitialAd: InterstitialAd) {
            interstitial = interstitialAd
          }

          override fun onAdFailedToLoad(p0: LoadAdError) {
            interstitial = null
          }
        })
    }

    fun initListeners() {
      interstitial?.fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdDismissedFullScreenContent() {
        }

        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
        }

        override fun onAdShowedFullScreenContent() {
          interstitial = null
        }
      }
    }

    fun checkCounter(activity: AppCompatActivity) {
      sharedPrefs.addInOneCounterAd()
      if (sharedPrefs.getAdCount() == 6) {
        this.showAds(activity)
        sharedPrefs.resetAdCount()
        initAds(activity)
      }
    }

    fun showAddDirectly(activity: AppCompatActivity) {
      this.showAds(activity)
      initAds(activity)
    }

    private fun showAds(activity: AppCompatActivity) {
      Logger.d("Show add")
      interstitial?.show(
        activity
      )
    }

    fun initLoad(adView: AdView) {
      /*AdView*/
      val adRequest = AdRequest.Builder().build()
      adView.loadAd(adRequest)
    }
    fun getLanguagePhone(): Boolean {
      val language = Locale.getDefault().displayLanguage.toString().lowercase(Locale.ROOT)
      return language == "english"
    }
  }
}