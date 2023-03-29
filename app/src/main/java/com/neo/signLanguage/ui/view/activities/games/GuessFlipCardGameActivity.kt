package com.neo.signLanguage.ui.view.activities.games


import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.ActivityGamesFlipCardBinding
import com.neo.signLanguage.ui.view.activities.composables.MyMaterialTheme
import com.neo.signLanguage.ui.view.activities.composables.TimeIsUpDialog
import com.neo.signLanguage.ui.view.activities.composables.TimerScreen
import com.neo.signLanguage.ui.view.activities.composables.backIcon
import com.neo.signLanguage.utils.AdUtils.Companion.checkCounter
import com.neo.signLanguage.utils.DialogGameDC
import com.neo.signLanguage.utils.GameResult

import com.neo.signLanguage.utils.GamesUtils.Companion.getRandomToFindEquals
import com.neo.signLanguage.utils.Utils
import com.neo.signLanguage.utils.Utils.Companion.getHandColor
import com.neo.signLanguage.utils.Utils.Companion.getHandCurrentColor
import com.neo.signLanguage.utils.Utils.Companion.getStringByIdName
import com.neo.signLanguage.utils.Utils.Companion.setColorByDifficulty
import com.neo.signLanguage.utils.Utils.Companion.vibratePhone
import com.wajahatkarim.flippable.Flippable
import com.wajahatkarim.flippable.FlippableController
import java.util.*


class GuessFlipCardGameActivity : AppCompatActivity() {

  private lateinit var binding: ActivityGamesFlipCardBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityGamesFlipCardBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val difficulty = intent.getSerializableExtra("difficulty") as Difficulty
    binding.composeViewGamesFlipCard.setContent {
      MyMaterialTheme(
        content = {
          Content(onClick = { onBackPressed() }, difficulty)
        }
      )
    }
  }

  @Composable
  fun Content(
    onClick: () -> Unit,
    difficulty: Difficulty,
  ) {

    val context = LocalContext
    val getDifficulty = generateDifficulty(difficulty)
    val time = remember { mutableStateOf(getDifficulty.timeInSeconds) }
    val xd = remember { mutableStateOf(getRandomToFindEquals(getDifficulty.pair.first)) }

    val flipsStates =
      remember { mutableStateListOf(*Array(xd.value.size) { FlippableController() }) }
    var flipSEnable =
      remember { mutableStateListOf(*Array(xd.value.size) { true }) }

    val flippedElements = remember { mutableStateListOf<String>() }
    val blockedElements = remember { mutableStateListOf<Int>() }

    val showTimesUpDialog = remember { mutableStateOf(false) }
    val showCompletedSuccessDialog = remember { mutableStateOf(false) }
    val startTimer = remember { mutableStateOf(true) }
    val timerPaused = remember { mutableStateOf(false) }

    val correctSound = remember {
      MediaPlayer.create(this@GuessFlipCardGameActivity, R.raw.correct2_sound)
    }
    val wrongSound = remember {
      MediaPlayer.create(this@GuessFlipCardGameActivity, R.raw.wrong_sound)
    }
    val loseSound = remember {
      MediaPlayer.create(this@GuessFlipCardGameActivity, R.raw.wrong_sound)
    }
    DisposableEffect(wrongSound) {
      onDispose {
        wrongSound.release()
        loseSound.release()
        correctSound.release()
      }
    }

    LaunchedEffect(xd.value) {

      startTimer.value = true
      timerPaused.value = false
      // Crear una copia inmutable de flipsStates
      val flipsStatesCopy = flipsStates.toList()

      // Reinicializar los estados de flipsStates
      flipsStatesCopy.forEach { it.flipToFront() }

      // Restablecer los estados de flipSEnable
      flipSEnable = List(xd.value.size) { true }.toMutableStateList()

      // Reinicializar los estados de flippedElements
      flippedElements.clear()

      // Reinicializar los estados de blockedElements
      blockedElements.clear()
      time.value = getDifficulty.timeInSeconds
    }

    fun verifyFlipMatch() {
      if (flippedElements.size == 2) {
        if (flippedElements[0] == flippedElements[1]) {
          Utils.playCorrectSound(this@GuessFlipCardGameActivity, correctSound)
          val searchResult =
            xd.value.withIndex().filter { (_, sign) -> sign.letter == flippedElements[0] }
          val indices = searchResult.map { (index, _) -> index }
          indices.forEach {
            flipSEnable[it] = false
          }
          flippedElements.clear()
          blockedElements.clear()
          /*Verify is all are Flips is disable*/
          if (flipSEnable.none { it }) {
         /*   checkCounter(this@GuessFlipCardGameActivity)
            timerPaused.value = true
            showCompletedSuccessDialog.value = true*/
            var xd = DialogGameDC(
              title = "Level Completed",
              subtitle = "Do you want to go to the next level?",
              audio = R.raw.wrong_sound,
              image = R.drawable.ic_help_outline,
              buttonText = "Go to next level",
              GameResult.WIN
            )
            goResultScreen(this@GuessFlipCardGameActivity,xd)
            finish()
          }
        } else {
          Utils.playCorrectSound(this@GuessFlipCardGameActivity, wrongSound)
          vibratePhone(this@GuessFlipCardGameActivity)
          flipsStates[blockedElements[0]].flipToFront()
          flipsStates[blockedElements[1]].flipToFront()
          blockedElements.clear()
          flippedElements.clear()
        }
      }
    }
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 8.dp),
    ) {
      if (showTimesUpDialog.value) {
        TimeIsUpDialog(
          onTryAgainClick = {
            Utils.playCorrectSound(this@GuessFlipCardGameActivity, loseSound)
            showTimesUpDialog.value = false
            xd.value = getRandomToFindEquals(getDifficulty.pair.first)
          },
          onGoBackClick = {
            onClick()
          },
          {
            onClick()
          },
          true,
          getStringByIdName(LocalContext.current, "times_over"),
          getStringByIdName(LocalContext.current, "time_up"),
          getStringByIdName(LocalContext.current, "retry"),
          getStringByIdName(LocalContext.current, "go_back"),
        )
      }
      if (showCompletedSuccessDialog.value) {
        TimeIsUpDialog(
          onTryAgainClick = {
            /*onClick()*/
            goNextLevel(this@GuessFlipCardGameActivity, difficulty)
          },
          onGoBackClick = {
            xd.value = getRandomToFindEquals(getDifficulty.pair.first)
            onClick()
          },
          onDismissRequest = {
            showCompletedSuccessDialog.value = false
          },
          false,
          getStringByIdName(LocalContext.current, "level_completed"),
          getStringByIdName(LocalContext.current, "try_next_level"),
          getStringByIdName(LocalContext.current, "go_back"),
          getStringByIdName(LocalContext.current, "go_back"),
        )
      }
      Column() {
        TimerScreen(
          onTimerEnd = {
            /*checkCounter(this@GuessFlipCardGameActivity)
            */
            showTimesUpDialog.value = true
          },
          timeInSeconds = time.value,
          color = getDifficulty.colorDifficulty,
          timerActive = startTimer,
          timerPaused = timerPaused,
        )
        Box(
          modifier = Modifier
            .align(Alignment.Start)
            .padding(16.dp)
        ) {
          backIcon(
            onClick = {
              onClick()
            }
          )
        }
        Box(
          modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ) {
          LazyVerticalGrid(
            columns = GridCells.Fixed(getDifficulty.pair.second),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.align(Alignment.Center),
            content = {
              itemsIndexed(xd.value) { index, sign ->
                Flippable(
                  onFlippedListener = {
                    if (!blockedElements.contains(index)) {
                      if (flippedElements.size < 2) {
                        flippedElements.add(sign.letter)
                        blockedElements.add(index)
                        verifyFlipMatch()
                      }
                    } else {
                      flippedElements.remove(sign.letter)
                      blockedElements.remove(index)
                    }
                  },
                  flipEnabled = flipSEnable[index],
                  frontSide = {
                    Card(
                      modifier = Modifier
                        .fillMaxSize()
                        .border(
                          width = 2.dp,
                          color = getHandCurrentColor(context.current),
                          shape = RoundedCornerShape(8.dp)
                        ),
                      shape = RoundedCornerShape(8.dp),
                      elevation = 6.dp,
                    ) {
                      Box() {
                        Image(
                          painter = painterResource(id = R.drawable.ic_help_outline),
                          contentDescription = null,
                          colorFilter = getHandColor(context.current),
                          modifier = Modifier
                            .fillMaxSize()
                            .aspectRatio(1f)
                            .padding(4.dp)
                        )
                      }
                    }
                  },
                  backSide = {
                    Card(
                      shape = RoundedCornerShape(8.dp),
                      elevation = 6.dp,
                      modifier = Modifier
                        .fillMaxSize()
                        .border(
                          width = 2.dp,
                          color = getHandCurrentColor(context.current),
                          shape = RoundedCornerShape(8.dp)
                        ),
                    ) {
                      Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                      ) {
                        Image(
                          painter = painterResource(id = sign.image),
                          contentDescription = null,
                          colorFilter = getHandColor(context.current),
                          modifier = Modifier
                            .fillMaxSize()
                            .aspectRatio(1f)
                            .padding(8.dp)
                        )
                        Text(
                          sign.letter.uppercase(Locale.getDefault()),
                          textAlign = TextAlign.Center,
                          fontSize = (28 - getDifficulty.pair.second).sp,
                          fontWeight = FontWeight(500),
                          color = getHandCurrentColor(context.current)
                        )
                      }
                    }
                  },
                  flipController = flipsStates[index],
                )
              }
            }
          )
        }
      }
    }
  }
}


enum class Difficulty {
  EASY,
  MEDIUM,
  HARD,
  VERY_HARD,
}

data class GenerateDifficulty(
  val pair: Pair<Int, Int>,
  val timeInSeconds: Int,
  var colorDifficulty: Color
)

fun generateDifficulty(difficulty: Difficulty): GenerateDifficulty {
  return when (difficulty) {
    Difficulty.EASY -> GenerateDifficulty(Pair(3, 3), 30, setColorByDifficulty(difficulty))
    Difficulty.MEDIUM -> GenerateDifficulty(Pair(6, 4), 45, setColorByDifficulty(difficulty))
    Difficulty.HARD -> GenerateDifficulty(Pair(8, 4), 60, setColorByDifficulty(difficulty))
    Difficulty.VERY_HARD -> GenerateDifficulty(Pair(18, 6), 90, setColorByDifficulty(difficulty))
  }
}

fun getNextDifficulty(difficulty: Difficulty): Difficulty {
  val values = Difficulty.values()
  val currentIndex = values.indexOf(difficulty)
  return if (currentIndex == values.lastIndex) {
    difficulty
  } else {
    values[currentIndex + 1]
  }
}

fun goNextLevel(context: Context, difficulty: Difficulty) {
  val intent = Intent(context, GuessFlipCardGameActivity::class.java)
  intent.putExtra("difficulty", getNextDifficulty(difficulty))
  context.startActivity(intent)
}
fun goResultScreen(context: Context,dialogGameDC: DialogGameDC) {

  val intent = Intent(context, GameResultActivity::class.java)
  intent.putExtra("dialogGameDC", dialogGameDC)
  context.startActivity(intent)
}
/*


@Composable
fun GoNextLevelWrapper(context: Context, difficulty: Difficulty) {
  goNextLevel(context, difficulty)
}*/
