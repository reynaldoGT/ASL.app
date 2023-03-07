package com.neo.signLanguage.ui.view.fragments


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier


import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.neo.signLanguage.data.models.Sign
import com.neo.signLanguage.databinding.ActivityGamesFlipCardBinding

import com.neo.signLanguage.utils.DataSign
import com.wajahatkarim.flippable.Flippable
import com.wajahatkarim.flippable.FlippableController


class GuessFlipCardGameActivity : AppCompatActivity() {

  private lateinit var binding: ActivityGamesFlipCardBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityGamesFlipCardBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.composeViewGamesFlipCard.setContent {
      Content(onClick = { onBackPressed() })
    }
  }
}

@Composable
fun Content(
  onClick: () -> Unit,
) {
  val xd = DataSign.getRandomToFindEquals(3)

  val key = "2"
  val flipsStates =
    remember(key) { mutableStateListOf(*Array(xd.data.size) { FlippableController() }) }
  val flipSEnable =
    remember(key) { mutableStateListOf(*Array(xd.data.size) { true }) }

  val flippedElements = remember { mutableStateListOf<String>() }
  val blockedElements = remember { mutableStateListOf<Int>() }

  fun verifyFlipMatch() {
    Log.d("verifyFlipMatch FN", "flippedElements: ${flippedElements.size}")

    if (flippedElements.size == 2) {
      if (flippedElements[0] == flippedElements[1]) {
        val searchResult =
          xd.data.withIndex().filter { (_, sign) -> sign.letter == flippedElements[0] }
        val indices = searchResult.map { (index, _) -> index }
        indices.forEach {
          flipSEnable[it] = false
        }
        flippedElements.clear()
        blockedElements.clear()
        /*Verify is all are Flips is disable*/
        if(flipSEnable.none { it }){
          onClick()
        }
      } else {
        flipsStates[blockedElements[0]].flipToFront()
        flipsStates[blockedElements[1]].flipToFront()
        blockedElements.clear()
        flippedElements.clear()
      }
    }
  }

  Column() {
    LazyVerticalGrid(
      columns = GridCells.Fixed(3),
      content = {
        itemsIndexed(xd.data) { index, sign ->
          Flippable(
            onFlippedListener = {
              if (!blockedElements.contains(index)) {
                Log.d("TAG", "Elemento agregado: ${index}")
                if (flippedElements.size < 2) {
                  flippedElements.add(sign.letter)
                  blockedElements.add(index)
                  verifyFlipMatch()
                }
              }else{
                flippedElements.remove(sign.letter)
                blockedElements.remove(index)
              }
            },
            flipEnabled = flipSEnable[index],
            frontSide = {
              Card() {
                Text(
                  "Adivina we",
                )
              }
            },
            backSide = {
              Card() {
                Card() {
                  Column() {
                    Image(
                      painter = painterResource(id = sign.image),
                      contentDescription = null,
                      modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(1f)
                        .padding(8.dp)
                    )
                    Text(
                      sign.letter,
                    )
                  }
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
