package com.neo.signLanguage.views.activities

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.ActivityDetailsBinding

import com.neo.signLanguage.views.activities.TabNavigatorActivity.Companion.getColorShared
import com.neo.signLanguage.views.activities.TabNavigatorActivity.Companion.pref
import com.orhanobut.logger.Logger


class DetailsSingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myIntent = intent

        val letter = myIntent.getStringExtra("letter")
        val type = myIntent.getStringExtra("type")?.capitalize() // will return "SecondKeyValue"
        val netWorkImage = myIntent.getBooleanExtra("networkImage", false)
        val image = intent.getStringExtra("image")


        Logger.d(image)
        binding.textView.text = letter
        if (netWorkImage && image != null) {
            Glide.with(this)
                .asGif()
                .load(image)
                .placeholder(R.drawable.ic_0_number)
                .error(R.drawable.ic_x_letter)
                .into(binding.image)
        } else {
            binding.image.setImageResource(intent.getIntExtra("image", R.drawable.circle_shape))
        }

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