package com.neo.signLanguage.ui.view.fragments


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext


import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.ActivityGamesFlipCardBinding
import com.neo.signLanguage.ui.view.activities.MainActivity.Companion.sharedPrefs
import com.neo.signLanguage.ui.view.activities.composables.MyMaterialTheme
import com.neo.signLanguage.ui.view.activities.composables.backIcon

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
          Content(onClick = { onBackPressed() }, difficulty)
        }
      )
    }
  }
}

@Composable
fun Content(
  onClick: () -> Unit,
  difficulty: Difficulty
) {
  val context = LocalContext
  val getDiff = generateDifficulty(difficulty)
  var xd = remember { mutableStateOf(DataSign.getRandomToFindEquals(getDiff.first)) }

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
      LazyVerticalGrid(
        columns = GridCells.Fixed(getDiff.second),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
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
                  elevation = 8.dp,
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
                  elevation = 8.dp,
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
                      fontSize = (28 - 6).sp,
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

enum class Difficulty {
  EASY,
  MEDIUM,
  HARD,
  VERY_HARD,
}

fun generateDifficulty(difficulty: Difficulty): Pair<Int, Int> {
  return when (difficulty) {
    Difficulty.EASY -> Pair(3, 3)
    Difficulty.MEDIUM -> Pair(6, 4)
    Difficulty.HARD -> Pair(8, 4)
    Difficulty.VERY_HARD -> Pair(18, 6)

  }

}