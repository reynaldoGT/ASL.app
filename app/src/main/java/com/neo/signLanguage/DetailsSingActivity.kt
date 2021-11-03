package com.neo.signLanguage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.neo.signLanguage.databinding.ActivityIntersitialBinding
import android.content.Intent
import android.R


class DetailsSingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntersitialBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntersitialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myIntent = intent

        val image = intent.getIntExtra("image", R.drawable.btn_star_big_off)
        val letter = myIntent.getStringExtra("letter") // will return "SecondKeyValue"
        val type = myIntent.getStringExtra("type") // will return "SecondKeyValue"


        binding.textView.text = letter
        binding.image.setImageResource(image)

        binding.settingsToolbar.title = "$type $letter"
        this.setSupportActionBar(binding.settingsToolbar)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)


    }


}