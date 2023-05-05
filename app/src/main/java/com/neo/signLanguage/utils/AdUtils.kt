package com.neo.signLanguage.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.neo.signLanguage.utils.SharedPreferences.addInOneCounterAd
import com.neo.signLanguage.utils.SharedPreferences.getAdCount
import com.neo.signLanguage.utils.SharedPreferences.resetAdCount
import com.neo.signLanguage.utils.Utils.Companion.getStringByIdName
import com.orhanobut.logger.Logger
import java.util.*


class AdUtils {
  companion object {
    private var isAdLoaded = false
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
            isAdLoaded = true
          }

          override fun onAdFailedToLoad(p0: LoadAdError) {
            interstitial = null
            isAdLoaded = false
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

    private fun showInterstitialAd(activity: AppCompatActivity) {
      if (isAdLoaded) {
        interstitial?.show(activity)
        isAdLoaded = false
        resetAdCount(activity.applicationContext)
      }
    }

    fun checkAdCounter(activity: AppCompatActivity) {
      Logger.d(
        "AdUtils :${getAdCount(activity.applicationContext)}",
        "valor==>  ${getAdCount(activity.applicationContext)}"
      )
      addInOneCounterAd(activity.applicationContext)
      Logger.d(
        "AdUtils :${getAdCount(activity.applicationContext)}",
        "valor==>  ${getAdCount(activity.applicationContext)}"
      )
      if (getAdCount(activity.applicationContext) == 7) {
        showInterstitialAd(activity)
        resetAdCount(activity.applicationContext)
        return
      }
      if (getAdCount(activity.applicationContext) > 7) {
        showInterstitialAd(activity)
        resetAdCount(activity.applicationContext)
      }
    }


    fun showAddDirectly(activity: AppCompatActivity) {
      this.showAds(activity)
      initAds(activity)
    }

    private fun showAds(activity: AppCompatActivity) {
      interstitial?.show(
        activity
      )
    }

    fun initLoad(adView: AdView) {
      // Cargar el anuncio en AdView
      val adRequest = AdRequest.Builder().build()
      adView.loadAd(adRequest)
    }


    fun getLanguagePhone(): Boolean {
      val language = Locale.getDefault().displayLanguage.toString().lowercase(Locale.ROOT)
      return language == "english"
    }
  }
}