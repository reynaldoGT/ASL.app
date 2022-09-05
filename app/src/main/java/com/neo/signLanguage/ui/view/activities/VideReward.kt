package com.neo.signLanguage.ui.view.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.neo.signLanguage.R
import com.neo.signLanguage.utils.Utils


class VideReward : AppCompatActivity() {
  private var mRewardedAd: RewardedAd? = null
  private var TAG = "MainActivity"

  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_vide_reward)

    val adRequest = AdRequest.Builder().build()

    RewardedAd.load(
      this,
      Utils.getStringByIdName(this, "test_reward_id"),
      adRequest,
      object : RewardedAdLoadCallback() {
        override fun onAdFailedToLoad(adError: LoadAdError) {
          Log.d(TAG, adError.toString())
          mRewardedAd = null

        }

        override fun onAdLoaded(rewardedAd: RewardedAd) {

          Log.d(TAG, "Ad was loaded.")
          mRewardedAd = rewardedAd
          showRewardAdd()


          mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
              // Called when a click is recorded for an ad.
              Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
              // Called when ad is dismissed.
              // Set the ad reference to null so you don't show the ad a second time.
              Log.d(TAG, "Ad dismissed fullscreen content.")
              mRewardedAd = null
              finish()
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
              // Called when ad fails to show.
              Log.e(TAG, "Ad failed to show fullscreen content.")
              mRewardedAd = null
            }

            override fun onAdImpression() {
              // Called when an impression is recorded for an ad.
              Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
              // Called when ad is shown.
              Log.d(TAG, "Ad showed fullscreen content.")
            }
          }
        }
      })

  }
  private fun showRewardAdd(){
    if (mRewardedAd != null) {
      mRewardedAd?.show(this, OnUserEarnedRewardListener() {
        fun onUserEarnedReward(rewardItem: RewardItem) {
          var rewardAmount = rewardItem.amount
          var rewardType = rewardItem.type
          Log.d(TAG, "User earned the reward.")
        }
      })
    } else {
      Log.d(TAG, "The rewarded ad wasn't ready yet.")
    }
  }
}