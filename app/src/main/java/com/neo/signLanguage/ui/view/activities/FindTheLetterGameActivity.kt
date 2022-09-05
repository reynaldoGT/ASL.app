package com.neo.signLanguage.ui.view.activities


import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout.LayoutParams
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.neo.signLanguage.adapters.AdapterGame
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.ClickListener
import com.neo.signLanguage.databinding.ActivityFindLetterGameBinding
import com.neo.signLanguage.ui.viewModel.GameViewModel
import com.neo.signLanguage.utils.AdUtils
import com.neo.signLanguage.utils.Utils.Companion.vibratePhone
import java.util.*

class FindTheLetterGameActivity : AppCompatActivity() {
  private lateinit var binding: ActivityFindLetterGameBinding
  private lateinit var adapter: AdapterGame
  private var intentsNumber: Int = 0

  private val model: GameViewModel by viewModels()

  var record: Int = 0
  var difficulty: String = ""
  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    binding = ActivityFindLetterGameBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val myIntent = intent
    difficulty = myIntent.getStringExtra("difficulty")!!

    when (difficulty) {
      "easy" -> {
        intentsNumber = 3
        model.setIntents(7)
      }
      "medium" -> {
        intentsNumber = 5
        model.setIntents(5)
      }
      "hard" -> {
        intentsNumber = 8
        model.setIntents(3)
      }
    }
    model.getRandomToFindLetter(intentsNumber)
    binding.currentRecord.text = getString(
      R.string.current_record,
      MainActivity.sharedPrefs.getMemoryRecord(difficulty).toString()
    )

    initRecyclerView(this)
    livesIcon()
    model.intents.observe(this) {
      if (it == 0) {
        Snackbar.make(
          this@FindTheLetterGameActivity.findViewById(android.R.id.content),
          getString(R.string.correct),
          Snackbar.LENGTH_SHORT,
        ).setBackgroundTint(ContextCompat.getColor(this, R.color.red_dark)).show()
        AdUtils.checkCounter(this)
        saveRecord()
        super.onBackPressed()
      }
    }
  }

  private fun livesIcon() {
    binding.livesContainer.removeAllViews()
    for (i in 0 until model.intents.value!!) {
      val imageView = ImageView(this)
      imageView.setImageDrawable(
        ContextCompat.getDrawable(
          this,
          R.drawable.ic_heart
        )
      )
      val params = RelativeLayout.LayoutParams(
        LayoutParams.WRAP_CONTENT,
        LayoutParams.WRAP_CONTENT
      )

      params.setMargins(10, 0, 0, 0)
      imageView.layoutParams = params
      binding.livesContainer.addView(imageView)
    }

  }

  private fun initRecyclerView(context: Context) {

    model.randomGameLetters
      .observe(this) {
        binding.currentAnswer.text =
          if (it.correctAnswer.type == "letter")
            getString(
              R.string.game_find_game_title_letter,
              it.correctAnswer.letter.uppercase(Locale.getDefault())
            )
          else getString(
            R.string.game_find_game_title_number,
            it.correctAnswer.letter.uppercase(Locale.getDefault())
          )
        binding.currentLetterAnswer.text =
          it.correctAnswer.letter.uppercase(Locale.getDefault())
        adapter =
          AdapterGame(
            this,
            it.data,
            object : ClickListener {
              override fun onClick(v: View?, position: Int) {
                /*model.setCurrentMessage(it!![position].sing, false)*/
                if ((it.data[position].letter) == it.correctAnswer.letter) {
                  record++
                  Snackbar.make(
                    this@FindTheLetterGameActivity.findViewById(android.R.id.content),
                    getString(R.string.correct),
                    Snackbar.LENGTH_SHORT,
                  ).setBackgroundTint(ContextCompat.getColor(context, R.color.green_dark)).show()
                  model.getRandomToFindLetter(intentsNumber)
                } else {
                  if (MainActivity.sharedPrefs.getVibration()) {
                    vibratePhone(applicationContext, 200)
                  }
                  model.setIntents(-1)
                  livesIcon()
                }
              }
            })
        binding.gridListSing.layoutManager =
          GridLayoutManager(this, if (difficulty == "hard") 3 else 2)
        binding.gridListSing.adapter = adapter
      }
  }

  private fun saveRecord() {
    if (MainActivity.sharedPrefs.getMemoryRecord(difficulty) < record) {
      MainActivity.sharedPrefs.setMemoryRecord(difficulty, record)
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    saveRecord()
    finish()
    return true
  }
}

