package com.neo.signLanguage.ui.view.fragments


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.material.snackbar.Snackbar
import com.neo.signLanguage.R
import com.neo.signLanguage.data.models.Sign

import com.neo.signLanguage.databinding.ActivityGamesFlipCardBinding
import com.neo.signLanguage.ui.view.activities.MainActivity
import com.neo.signLanguage.ui.viewModel.GameViewModel
import com.neo.signLanguage.utils.Game
import com.neo.signLanguage.utils.Utils
import com.wajahatkarim.flippable.Flippable
import com.wajahatkarim.flippable.rememberFlipController


class GuessFlipCardGameActivity : AppCompatActivity() {

  private lateinit var binding: ActivityGamesFlipCardBinding
  private val model: GameViewModel by viewModels()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityGamesFlipCardBinding.inflate(layoutInflater)
    setContentView(binding.root)
    model.getRandomToFindEquals(5)
    binding.greeting.setContent {
      Content()
    }
  }
}

@Composable
fun Content(gameViewModel: GameViewModel = viewModel()) {

  val context = LocalContext.current
  val randomletters by gameViewModel.randomGameLetters.observeAsState(
    Game(
      ArrayList(), Sign(
        "", 0, "",
      )
    )
  )
  LazyVerticalGrid(
/*          columns = GridCells.Adaptive(128.dp),*/
    columns = GridCells.Fixed(2),
    content = {
      items(randomletters.data.size) { index ->
        if(index == 0){
          Text(text = "hola")
        }
        Flippable(
          frontSide = {
            Card() {
              Image(
                painter = painterResource(id = randomletters.data[index].image),
                contentDescription = null,
                modifier = Modifier
                  .fillMaxSize()
                  .aspectRatio(1f)
                  .padding(8.dp)
              )
            }
          },
          backSide = {
            Card() {
              Text(
                randomletters.data[index].letter,
              )
            }
          },

          flipController = rememberFlipController(),

          // Other optional parameters
        )
      }
    }
  )
}
