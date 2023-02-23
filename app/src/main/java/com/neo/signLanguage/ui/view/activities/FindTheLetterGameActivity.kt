package com.neo.signLanguage.ui.view.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.ActivityFindLetterGameBinding
import com.neo.signLanguage.ui.view.activities.MainActivity.Companion.sharedPrefs
import com.neo.signLanguage.ui.viewModel.GameViewModel
import com.neo.signLanguage.utils.AdUtils
import com.neo.signLanguage.utils.Utils.Companion.vibratePhone
import java.util.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neo.signLanguage.data.models.Sign
import com.neo.signLanguage.ui.view.activities.composables.MyMaterialTheme
import com.neo.signLanguage.utils.Game
import kotlin.collections.ArrayList
import android.graphics.Color as AndroidColor


class FindTheLetterGameActivity : AppCompatActivity() {
  private lateinit var binding: ActivityFindLetterGameBinding
  private var numberElements: Int = 0
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
        numberElements = 4
        model.setIntents(7)
      }
      "medium" -> {
        numberElements = 6
        model.setIntents(5)
      }
      "hard" -> {
        numberElements = 9
        model.setIntents(3)
      }
      "veryHard" -> {
        numberElements = 12
        model.setIntents(3)
      }
    }
    model.getRandomToFindLetter(numberElements)
    binding.findLetterGameComposeView.setContent {
      MyMaterialTheme(
        content = {
          ContainerLayout()
        }
      )
    }

  }

  private fun showSnackBar(message: String, color: Int) {
    Snackbar
      .make(
        this@FindTheLetterGameActivity.findViewById(android.R.id.content),
        message,
        Snackbar.LENGTH_SHORT,
      )
      .setBackgroundTint(
        ContextCompat.getColor(
          this@FindTheLetterGameActivity,
          color
        )
      )
      .setTextColor(AndroidColor.WHITE)
      .show()
  }

  @Composable
  fun ContainerLayout(gameViewModel: GameViewModel = viewModel()) {
    val randomletters by gameViewModel.randomGameLetters.observeAsState(
      Game(
        ArrayList(), Sign(
          "", 0, "",
        )
      )
    )
    val intents by gameViewModel.intents.observeAsState(1)
    if (intents == 0) {
      Snackbar.make(
        this@FindTheLetterGameActivity.findViewById(android.R.id.content),
        getString(R.string.incorrect),
        Snackbar.LENGTH_SHORT,
      ).setBackgroundTint(ContextCompat.getColor(this, R.color.red_dark)).show()
      AdUtils.checkCounter(this)
      saveRecord()
      super.onBackPressed()
    }

    Box() {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp),
        /*verticalArrangement = Arrangement.SpaceBetween,*/
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.End,
        ) {
          Box() {
            Row() {
              LazyRow() {
                items(intents) {
                  Box() {
                    Column() {
                      Image(
                        painter = painterResource(id = R.drawable.ic_heart),
                        contentDescription = "Heart",
                        modifier = Modifier
                          .padding(5.dp)
                          .size(30.dp)
                      )
                    }
                  }
                }
              }
            }
          }
        }

        Text(
          text = if (randomletters.correctAnswer.type == "letter")
            getString(
              R.string.game_find_game_title_letter,
              randomletters.correctAnswer.letter.uppercase(Locale.getDefault())
            )
          else getString(
            R.string.game_find_game_title_number,
            randomletters.correctAnswer.letter.uppercase(Locale.getDefault())
          )

        )
        Text(
          randomletters.correctAnswer.letter.uppercase(Locale.getDefault())
        )
        LazyVerticalGrid(
/*          columns = GridCells.Adaptive(128.dp),*/
          columns = GridCells.Fixed(if (difficulty == "easy") 2 else 3),
          content = {
            items(randomletters.data.size) { index ->
              Card(
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                  .fillMaxSize()
                  .padding(8.dp)
                  .clickable {
                    if ((randomletters.data[index].letter) == randomletters.correctAnswer.letter) {
                      record++
                      showSnackBar(getString(R.string.correct), R.color.green_dark)
                      model.getRandomToFindLetter(numberElements)
                    } else {
                      if (sharedPrefs.getVibration()) {
                        vibratePhone(applicationContext, 50)
                      }
                      showSnackBar(getString(R.string.incorrect), R.color.red_dark)
                      model.setIntents(-1)
                    }
                  }
              ) {
                Image(
                  painter = painterResource(id = randomletters.data[index].image),
                  contentDescription = null,
                  colorFilter = ColorFilter.tint(
                    Color(
                      ContextCompat.getColor(
                        this@FindTheLetterGameActivity,
                        sharedPrefs.getColor()
                      )
                    )
                  ),
                  modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
                    .padding(8.dp)
                )
              }
            }
          }
        )
      }
    }

  }

  private fun saveRecord() {
    if (sharedPrefs.getMemoryRecord(difficulty) < record) {
      sharedPrefs.setMemoryRecord(difficulty, record)
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    saveRecord()
    finish()
    return true
  }
}

