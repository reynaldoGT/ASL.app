package com.neo.signLanguage.ui.view.activities

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.ActivityDetailsBinding
import com.neo.signLanguage.databinding.ActivityFindPairGameBinding

class FindPairGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFindPairGameBinding
    lateinit var frontAnim: AnimatorSet
    lateinit var backAnim: AnimatorSet
    var isFront = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindPairGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val scale = applicationContext.resources.displayMetrics.density
        binding.cardFront.cameraDistance = 8000 * scale
        binding.cardBack.cameraDistance = 8000 * scale

        frontAnim = AnimatorInflater.loadAnimator(
            applicationContext,
            R.animator.front_animation
        ) as AnimatorSet
        backAnim = AnimatorInflater.loadAnimator(
            applicationContext,
            R.animator.back_animation
        ) as AnimatorSet

        binding.cardContainer.setOnClickListener {
            rotate()
        }

    }

    fun rotate() {
        isFront = if (isFront) {
            frontAnim.setTarget(binding.cardFront)
            backAnim.setTarget(binding.cardBack)
            frontAnim.start()
            backAnim.start()
            false
        } else {
            frontAnim.setTarget(binding.cardBack)
            backAnim.setTarget(binding.cardFront)
            backAnim.start()
            frontAnim.start()
            true
        }
    }
}