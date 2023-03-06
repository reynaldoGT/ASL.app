package com.neo.signLanguage.ui.view.activities

import android.os.Bundle
import android.util.Log.d
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
import com.neo.signLanguage.utils.Utils.Companion.showSnackBarToGames
import java.util.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neo.signLanguage.data.models.Sign
import com.neo.signLanguage.ui.view.activities.composables.MyMaterialTheme
import com.neo.signLanguage.ui.view.activities.composables.backIcon
import com.neo.signLanguage.utils.Game
import kotlin.collections.ArrayList


class FindTheLetterGameActivity : AppCompatActivity() {
  private lateinit var binding: ActivityFindLetterGameBinding
  private var numberElements: Int = 0
  private val model: GameViewModel by viewModels()

  var record: Int = 0
  var difficulty: String = ""
  var intents = 0
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityFindLetterGameBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val myIntent = intent
    difficulty = myIntent.getStringExtra("difficulty")!!

    when (difficulty) {
      "easy" -> {
        numberElements = 4
        intents = (7)
      }
      "medium" -> {
        numberElements = 6
        intents = (5)
      }
      "hard" -> {
        numberElements = 9
        intents = (3)
      }
      "veryHard" -> {
        numberElements = 12
        intents = (2)
      }
    }
    model.getRandomToFindLetter(numberElements)
    binding.findLetterGameComposeView.setContent {
      MyMaterialTheme(
        content = {
          ContainerLayout(onClick = { onBackPressed() }, intents = intents)
        }
      )
    }
  }

  @Composable
  fun ContainerLayout(
    gameViewModel: GameViewModel = viewModel(),
    onClick: () -> Unit,
    intents: Int,
  ) {
    var remainingLives by remember { mutableStateOf(intents) }
    val allIntents = intents
    val randomLetters by gameViewModel.randomGameLetters.observeAsState(
      Game(
        ArrayList(), Sign(
          "", 0, "",
        )
      )
    )


    if (remainingLives == 0) {
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
        Box(
          modifier = Modifier
            .align(Alignment.Start)
            .padding(16.dp)
        ) {
          backIcon(
            onClick = {
              saveRecord()
              onClick()
            }
          )
        }
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.End,
        ) {
          Box() {
            Row {
              repeat(remainingLives) {
                HeartIcon(full = true)
              }
              repeat(allIntents - remainingLives) {
                HeartIcon(full = false)
              }
            }
          }
        }

        Text(
          text = if (randomLetters.correctAnswer.type == "letter")
            getString(
              R.string.game_find_game_title_letter,
              randomLetters.correctAnswer.letter.uppercase(Locale.getDefault())
            )
          else getString(
            R.string.game_find_game_title_number,
            randomLetters.correctAnswer.letter.uppercase(Locale.getDefault())
          )

        )
        Text(
          randomLetters.correctAnswer.letter.uppercase(Locale.getDefault())
        )
        LazyVerticalGrid(
/*          columns = GridCells.Adaptive(128.dp),*/
          columns = GridCells.Fixed(if (difficulty == "easy" || difficulty == "medium") 2 else 3),
          content = {
            items(randomLetters.data.size) { index ->
              Card(
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                  .fillMaxSize()
                  .padding(8.dp)
                  .clickable {
                    if ((randomLetters.data[index].letter) == randomLetters.correctAnswer.letter) {
                      record++
                      showSnackBarToGames(
                        getString(R.string.correct),
                        R.color.green_dark,
                        this@FindTheLetterGameActivity.findViewById(android.R.id.content),
                        this@FindTheLetterGameActivity,
                      )
                      model.getRandomToFindLetter(numberElements)
                    } else {
                      if (sharedPrefs.getVibration()) {
                        vibratePhone(applicationContext, 50)
                      }
                      showSnackBarToGames(
                        getString(R.string.incorrect),
                        R.color.red_dark,
                        this@FindTheLetterGameActivity.findViewById(android.R.id.content),
                        this@FindTheLetterGameActivity,
                      )
                      /*showSnackBar(getString(R.string.incorrect), R.color.red_dark)*/
                      /*model.setIntents(-1)*/
                      remainingLives--
                    }
                  }
              ) {
                Image(
                  painter = painterResource(id = randomLetters.data[index].image),
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

  @Composable
  fun HeartIcon(full: Boolean) {
    Icon(
      if (full) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
      contentDescription = "Favorite",
      tint = Color.Red,
      modifier = Modifier
        .size(40.dp)
        .padding(4.dp)
    )
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

