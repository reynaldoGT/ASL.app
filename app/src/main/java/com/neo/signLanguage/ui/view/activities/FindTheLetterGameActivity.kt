package com.neo.signLanguage.ui.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.neo.signLanguage.AdapterGame
import com.neo.signLanguage.adapters.ClickListener
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.ActivityFindLetterGameBinding
import com.neo.signLanguage.ui.viewModel.GameViewModel
import com.neo.signLanguage.utils.Utils.Companion.vibratePhone
import com.orhanobut.logger.Logger

class FindTheLetterGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFindLetterGameBinding
    private lateinit var adapter: AdapterGame
    private var intentsNumber: Int = 0

    private val model: GameViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindLetterGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intentsNumber = 3
        model.getRandomToFindLetter(intentsNumber)
        initRecyclerView()
        model.intents.observe(this) {
            if (it == 0) {
                super.onBackPressed()
            }
            binding.intents.text = getString(R.string.intents, it)
        }
    }

    private fun initRecyclerView() {

        model.randomGameLetters
            .observe(this) {
                binding.currentAnswer.text =
                    if (it.correctAnswer.type == "letter")
                        getString(
                            R.string.game_find_game_title_letter,
                            it.correctAnswer.letter
                        ) else getString(
                        R.string.game_find_game_title_number,
                        it.correctAnswer.letter
                    )
                adapter =
                    AdapterGame(
                        this,
                        it.data,
                        object : ClickListener {
                            override fun onClick(v: View?, position: Int) {
                                /*model.setCurrentMessage(it!![position].sing, false)*/
                                if ((it.data[position].letter) == it.correctAnswer.letter) {
                                    Logger.d("Show add")
                                    Log.d("vakue", position.toString())
                                    model.getRandomToFindLetter(intentsNumber)
                                } else {
                                    Logger.d("Show add")
                                    if (TabNavigatorActivity.sharedPrefs.getVibration()) {
                                        vibratePhone(applicationContext, 200)
                                    }
                                    model.setIntents(-1)
                                }
                            }
                        })
                binding.gridListSing.layoutManager = GridLayoutManager(this, 2)
                binding.gridListSing.adapter = adapter
            }
    }
}