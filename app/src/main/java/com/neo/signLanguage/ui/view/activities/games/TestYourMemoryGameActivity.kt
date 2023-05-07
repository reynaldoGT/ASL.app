package com.neo.signLanguage.ui.view.activities.games

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.ActivityTestYourMemoryGameBinding
import com.neo.signLanguage.ui.viewModel.GameViewModel
import com.neo.signLanguage.utils.Utils.Companion.vibratePhone
import java.util.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neo.signLanguage.data.models.Sign
import com.neo.signLanguage.ui.view.activities.composables.MyMaterialTheme
import com.neo.signLanguage.ui.view.activities.composables.backIcon
import com.neo.signLanguage.utils.*

import com.neo.signLanguage.utils.SharedPreferences.getMemoryRecord
import com.neo.signLanguage.utils.SharedPreferences.isDarkTheme
import com.neo.signLanguage.utils.SharedPreferences.setMemoryRecord
import com.neo.signLanguage.utils.Utils.Companion.getHandColor
import com.neo.signLanguage.utils.Utils.Companion.getStringByIdName
import com.neo.signLanguage.utils.Utils.Companion.setColorByDifficulty
import kotlin.collections.ArrayList


class TestYourMemoryGameActivity : AppCompatActivity() {
  private lateinit var binding: ActivityTestYourMemoryGameBinding
  private var numberElements: Int = 0
  private val model: GameViewModel by viewModels()


  var record: Int = 0
  var difficulty: Difficulty = Difficulty.EASY
  var intents = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    /*Ad initialization*/
    AdUtils.initAds(this)
    AdUtils.initListeners()

    binding = ActivityTestYourMemoryGameBinding.inflate(layoutInflater)
    setContentView(binding.root)
    difficulty = intent.getSerializableExtra("difficulty") as Difficulty

    when (difficulty) {
      Difficulty.EASY -> {
        numberElements = 4
        intents = (7)
      }
      Difficulty.MEDIUM -> {
        numberElements = 6
        intents = (5)
      }
      Difficulty.HARD -> {
        numberElements = 9
        intents = (3)
      }
      Difficulty.VERY_HARD -> {
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
    val getRecordByDifficulty = remember { mutableStateOf(getMemoryRecord(this, difficulty)) }
    val currentRecord = remember { mutableStateOf(0) }

    val correctAnswerSound = remember {
      MediaPlayer.create(this@TestYourMemoryGameActivity, R.raw.correct2_sound)
    }
    val loseSound = remember {
      MediaPlayer.create(this@TestYourMemoryGameActivity, R.raw.lose_sound2)
    }
    val wrongSound = remember {
      MediaPlayer.create(this@TestYourMemoryGameActivity, R.raw.wrong_sound)
    }

    DisposableEffect(correctAnswerSound) {
      onDispose {
        correctAnswerSound.release()
        loseSound.release()
      }
    }

    if (remainingLives == 0) {
      val thisContext = this@TestYourMemoryGameActivity
      val dialogGameDC = DialogGameDC(
        getStringByIdName(thisContext, "you_lost"),
        getStringByIdName(thisContext, "better_luck"),
        R.raw.lose_sound2,
        getLoseIcons(),
        getStringByIdName(thisContext, "return_menu"),
        GameResult.LOSE,
      )
      saveRecord(this)
      goResultActivity(thisContext, dialogGameDC)
      AdUtils.checkAdCounter(this)
      finish()

    }
    val textStyle = TextStyle(
      color = MaterialTheme.colorScheme.onBackground,
      fontWeight = FontWeight.W600,
      fontSize = 16.sp,
    )
    Box() {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Box(
          modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
          ) {
            backIcon(
              onClick = {
                saveRecord(this@TestYourMemoryGameActivity)
                onClick()
              }
            )
            Column(
            ) {
              Text(
                style = textStyle,
                text = getStringByIdName(LocalContext.current, "hits") + ": " + currentRecord.value,
              )
              Text(
                style = textStyle,
                text = getStringByIdName(
                  LocalContext.current,
                  "record"
                ) + ": " + getRecordByDifficulty.value,
              )
            }
          }
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
          ),
          style = MaterialTheme.typography.titleLarge,
          color = if (isDarkTheme(LocalContext.current)) Color.LightGray else Color.DarkGray,
        )
        Text(
          randomLetters.correctAnswer.letter.uppercase(Locale.getDefault()),
          style = MaterialTheme.typography.titleMedium + TextStyle(
            fontWeight = FontWeight.Bold,
          ),
          color = if (isDarkTheme(LocalContext.current)) Color.White else Color.DarkGray,
          modifier = Modifier
            .padding(vertical = 4.dp)
        )
        Box(
          modifier = Modifier
            .fillMaxSize()
        ) {
          LazyVerticalGrid(
            columns = GridCells.Fixed(if (difficulty == Difficulty.EASY || difficulty == Difficulty.MEDIUM) 2 else 3),
            modifier = Modifier.align(Alignment.Center),
            content = {
              items(randomLetters.data.size) { index ->
                Card(
                  shape = RoundedCornerShape(8.dp),
                  modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .clickable {
                      if ((randomLetters.data[index].letter) == randomLetters.correctAnswer.letter) {
                        /*Check this*/
                        Utils.playCorrectSound(this@TestYourMemoryGameActivity, correctAnswerSound)
                        record++
                        currentRecord.value = record
                        if (currentRecord.value > getRecordByDifficulty.value) {
                          getRecordByDifficulty.value = currentRecord.value
                          saveRecord(this@TestYourMemoryGameActivity)
                        }
                        model.getRandomToFindLetter(numberElements)
                      } else {
                        vibratePhone(applicationContext)
                        Utils.playCorrectSound(this@TestYourMemoryGameActivity, wrongSound)
                        remainingLives--
                      }
                    }
                ) {
                  Image(
                    painter = painterResource(id = randomLetters.data[index].image),
                    contentDescription = null,
                    colorFilter = getHandColor(this@TestYourMemoryGameActivity),
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
  }

  @Composable
  fun HeartIcon(full: Boolean, color: Color = Color.Red) {
    Icon(
      if (full) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
      contentDescription = "Favorite",
      tint = setColorByDifficulty(difficulty),
      modifier = Modifier
        .size(40.dp)
        .padding(4.dp)
    )
  }

  private fun saveRecord(context: Context) {
    if (getMemoryRecord(context, difficulty) < record) {
      setMemoryRecord(context, difficulty, record)
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    saveRecord(this)
    finish()
    return true
  }
}

