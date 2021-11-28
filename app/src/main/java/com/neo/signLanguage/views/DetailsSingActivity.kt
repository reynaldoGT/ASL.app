package com.neo.signLanguage.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neo.signLanguage.databinding.ActivityDetailsBinding
import android.R
import com.neo.signLanguage.views.activities.MainActivity


class DetailsSingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myIntent = intent

        val image = intent.getIntExtra("image", R.drawable.btn_star_big_off)
        val letter = myIntent.getStringExtra("letter")
        val type = myIntent.getStringExtra("type")?.capitalize() // will return "SecondKeyValue"


        binding.textView.text = letter
        binding.image.setImageResource(image)

        if (MainActivity.pref.getColor() != 0)
            binding.image.setColorFilter(
                MainActivity.getColorShared(this)
            )
        binding.settingsToolbar.title = "$type $letter"
        this.setSupportActionBar(binding.settingsToolbar)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

    }


}