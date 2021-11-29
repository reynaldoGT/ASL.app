package com.neo.signLanguage.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.ActivityDetailsBinding
import com.neo.signLanguage.views.activities.TabNavigatorActivity.Companion.getColorShared
import com.neo.signLanguage.views.activities.TabNavigatorActivity.Companion.pref


class DetailsSingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myIntent = intent

        val image = intent.getIntExtra("image", R.drawable.circle_shape)
        val letter = myIntent.getStringExtra("letter")
        val type = myIntent.getStringExtra("type")?.capitalize() // will return "SecondKeyValue"


        binding.textView.text = letter
        binding.image.setImageResource(image)

        if (pref.getColor() != 0)
            binding.image.setColorFilter(
                getColorShared(this)
            )
        binding.settingsToolbar.title = "$type $letter"
        this.setSupportActionBar(binding.settingsToolbar)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

    }


}