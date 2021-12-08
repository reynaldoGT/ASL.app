package com.neo.signLanguage.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.ActivityDetailsBinding
import com.neo.signLanguage.views.activities.TabNavigatorActivity.Companion.getColorShared
import com.neo.signLanguage.views.activities.TabNavigatorActivity.Companion.sharedPrefs
import com.orhanobut.logger.Logger


class DetailsSignActivity : AppCompatActivity() {
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
        binding.imageTitle.text = letter
        if (netWorkImage && image != null) {
            Glide.with(this)
                .asGif()
                .load(image)
                .placeholder(R.drawable.ic_0_number)
                .error(R.drawable.ic_x_letter)
                .into(binding.image)
            binding.detailToolbar.title = letter
            binding.imageTitle.visibility = View.GONE
        } else {
            binding.image.setImageResource(intent.getIntExtra("image", R.drawable.circle_shape))
        }

        if (sharedPrefs.getColor() != 0)
            binding.image.setColorFilter(
                getColorShared(this)
            )
        binding.detailToolbar.title = "$type $letter"
        this.setSupportActionBar(binding.detailToolbar)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

    }


}