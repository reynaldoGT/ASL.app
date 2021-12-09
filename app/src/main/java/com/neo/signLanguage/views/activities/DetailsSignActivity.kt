package com.neo.signLanguage.views.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.ActivityDetailsBinding
import com.neo.signLanguage.views.activities.TabNavigatorActivity.Companion.getColorShared
import com.neo.signLanguage.views.activities.TabNavigatorActivity.Companion.sharedPrefs
import com.orhanobut.logger.Logger
import java.util.*
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target


class DetailsSignActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myIntent = intent

        val letter = myIntent.getStringExtra("letter")
        val type =
            myIntent.getStringExtra("type")?.capitalize(Locale.ROOT) // will return "SecondKeyValue"
        val netWorkImage = myIntent.getBooleanExtra("networkImage", false)
        val imageURL = intent.getStringExtra("imageUrl")

        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()


        binding.imageTitle.text = letter
        if (netWorkImage ) {
            Glide.with(applicationContext)
                .asGif()
                .load(imageURL)
                .placeholder(circularProgressDrawable)
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