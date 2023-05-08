package com.neo.signLanguage.ui.view.activities

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.WelcomeSliderAdapter
import com.neo.signLanguage.data.models.TutorialWelcome

import com.neo.signLanguage.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

  private lateinit var binding: ActivityWelcomeBinding
  private lateinit var welcomeSliderAdapter: WelcomeSliderAdapter
  private lateinit var arrayTutorialWelcome: ArrayList<TutorialWelcome>
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityWelcomeBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val showElementLiveData = MutableLiveData<Boolean>(false)
    showElementLiveData.observe(this) { showElement ->
      // Actualiza la visibilidad del elemento en función del valor de showElement
      /*binding.btnSkip.visibility = if (showElement) View.VISIBLE else View.GONE*/
      if (showElement) {
        if (binding.btnStart.alpha < 1f) {
          // El botón está invisible o semi-visible, así que lo animamos para hacerlo visible
          binding.btnStart.alpha = 0f
          binding.btnStart.visibility = View.VISIBLE
          ObjectAnimator.ofFloat(binding.btnStart, "alpha", 0f, 1f).apply {
            duration = 500 // duración de la animación en milisegundos
            start()
          }
        }
      } else {
        if (binding.btnStart.alpha > 0f) {
          // El botón está visible o semi-visible, así que lo animamos para hacerlo invisible
          binding.btnStart.alpha = 1f
          ObjectAnimator.ofFloat(binding.btnStart, "alpha", 1f, 0f).apply {
            duration = 500 // duración de la animación en milisegundos
            doOnEnd {
              binding.btnStart.visibility = View.GONE
            }
            start()
          }
        }
      }
    }

    arrayTutorialWelcome = ArrayList<TutorialWelcome>(
      arrayListOf(
        TutorialWelcome(
          "Welcome to Sign Language",
          "This is a tutorial for you to learn sign language",
          R.drawable.ic_launcher_background
        ),
        TutorialWelcome(
          "Welcome to Sign Language",
          "This is a tutorial for you to learn sign language",
          R.drawable.ic_launcher_background
        ),
        TutorialWelcome(
          "Welcome to Sign Language",
          "This is a tutorial for you to learn sign language",
          R.drawable.ic_launcher_background
        ),
        TutorialWelcome(
          "Welcome to Sign Language",
          "This is a tutorial for you to learn sign language",
          R.drawable.ic_launcher_background
        )
      )
    )

    welcomeSliderAdapter = WelcomeSliderAdapter(this, arrayTutorialWelcome)

    setUpViewPager(showElementLiveData)
    setupButtons()

  }

  private fun setUpViewPager(showElementLiveData: MutableLiveData<Boolean>) {

    binding.viewPagerWelcome.adapter = welcomeSliderAdapter

    //set the orientation of the viewpager using ViewPager2.orientation
    /*binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL*/
    binding.viewPagerWelcome.orientation = ViewPager2.ORIENTATION_HORIZONTAL

    //select any page you want as your starting page
    val currentPageIndex = 1
    binding.viewPagerWelcome.currentItem = currentPageIndex

    // registering for page change callback
    binding.viewPagerWelcome.registerOnPageChangeCallback(
      object : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
          super.onPageSelected(position)
          showElementLiveData.postValue(position == arrayTutorialWelcome.size - 1)

        }
      }
    )
  }

  fun setupButtons() {
    binding.btnSkip.setOnClickListener {
      startApplication()
    }
    binding.btnStart.setOnClickListener {
      startApplication()
    }
    binding.btnSkip.setOnClickListener {
      startApplication()
    }
  }

  fun startApplication() {
    /*Change this */
    val intent = Intent(this, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
  }
}