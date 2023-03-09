package com.neo.signLanguage.ui.view.fragments


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalContext


import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.ActivityGamesFlipCardBinding
import com.neo.signLanguage.ui.view.activities.composables.MyMaterialTheme
import com.neo.signLanguage.ui.view.activities.composables.TimerScreen
import com.neo.signLanguage.ui.view.activities.composables.backIcon
import com.neo.signLanguage.utils.AdUtils.Companion.checkCounter

import com.neo.signLanguage.utils.DataSign
import com.neo.signLanguage.utils.Utils.Companion.getHandColor
import com.neo.signLanguage.utils.Utils.Companion.getHandCurrentColor
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
          Content(onClick = { onBackPressed() }, difficulty, this)
        }
      )
    }
  }
}

@Composable
fun Content(
  onClick: () -> Unit,
  difficulty: Difficulty,
  guessFlipCardGameActivity: GuessFlipCardGameActivity
) {
  val context = LocalContext
  val getDifficulty = generateDifficulty(difficulty)
  var xd = remember { mutableStateOf(DataSign.getRandomToFindEquals(getDifficulty.pair.first)) }

  val flipsStates =
    remember { mutableStateListOf(*Array(xd.value.size) { FlippableController() }) }
  var flipSEnable =
    remember { mutableStateListOf(*Array(xd.value.size) { true }) }

  var flippedElements = remember { mutableStateListOf<String>() }
  var blockedElements = remember { mutableStateListOf<Int>() }

  LaunchedEffect(xd.value) {
    // Reinicializar los estados de flipsStates
    flipsStates.forEach { it.flipToFront() }

    // Reinicializar los estados de flipSEnable
    flipSEnable.forEachIndexed { index, mutableState ->
      flipSEnable[index] = true
    }

    // Reinicializar los estados de flippedElements
    flippedElements.clear()

    // Reinicializar los estados de blockedElements
    blockedElements.clear()
  }

  fun verifyFlipMatch() {
    if (flippedElements.size == 2) {
      if (flippedElements[0] == flippedElements[1]) {
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
          onClick()
          checkCounter(guessFlipCardGameActivity)
          /*xd.value = DataSign.getRandomToFindEquals(2)*/
        }
      } else {
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
      .padding(8.dp),

    ) {
    Column() {
      TimerScreen(
        onTimerEnd = {
          onClick()
        },
        timeInSeconds = getDifficulty.timeInSeconds
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
                      .fillMaxSize(),
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

enum class Difficulty {
  EASY,
  MEDIUM,
  HARD,
  VERY_HARD,
}

data class GenerateDifficulty(val pair: Pair<Int, Int>, val timeInSeconds: Int)

fun generateDifficulty(difficulty: Difficulty): GenerateDifficulty {
  return when (difficulty) {
    Difficulty.EASY -> GenerateDifficulty(Pair(3, 3), 30)
    Difficulty.MEDIUM -> GenerateDifficulty(Pair(6, 4), 45)
    Difficulty.HARD -> GenerateDifficulty(Pair(8, 4), 60)
    Difficulty.VERY_HARD -> GenerateDifficulty(Pair(18, 6), 90)
  }
}