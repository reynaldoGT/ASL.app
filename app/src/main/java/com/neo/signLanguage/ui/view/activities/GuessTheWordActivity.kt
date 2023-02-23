package com.neo.signLanguage.ui.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.ActivityGuessTheWordBinding

import com.neo.signLanguage.data.models.Sign
import com.neo.signLanguage.ui.view.activities.composables.MyMaterialTheme

import com.neo.signLanguage.utils.DataSign.Companion.generateListImageSign
import com.neo.signLanguage.utils.Utils.Companion.getRandomWord


import java.util.*


class GuessTheWordActivity : AppCompatActivity() {
  private lateinit var binding: ActivityGuessTheWordBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityGuessTheWordBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.ActivityGuessTheWordBindingComposeView.setContent {

      MyMaterialTheme(content = {
        RotatingImages()
      })
    }
  }

  /*private fun resetGenerateListImageSign() {
    val randomXd = getRandomWord()
    val generated = generateListImageSign(randomXd)
    binding.ActivityGuessTheWordBindingComposeView.setContent {
      RotatingImages(images = generated.data, correctWord = randomXd)
    }
  }*/

  @Composable
  fun RotatingImages() {
    var correctWord by remember { mutableStateOf(getRandomWord()) }
    var imagesStatus by remember { mutableStateOf(generateListImageSign(correctWord)) }
    var currentImageIndex by remember { mutableStateOf(0) }
    var lifes by remember { mutableStateOf(3) }
    var isButtonEnabled by remember { mutableStateOf(true) }
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var timer = remember { Timer() }

    val task = object : TimerTask() {
      override fun run() {
        isButtonEnabled = false
        currentImageIndex = (currentImageIndex + 1) % imagesStatus.data.size
        if (currentImageIndex == imagesStatus.data.size - 1) {
          timer.cancel()
          timer.purge()
          isButtonEnabled = true
        }
      }
    }

    LaunchedEffect(correctWord) {
      imagesStatus = generateListImageSign(correctWord)
      currentImageIndex = 0
      isButtonEnabled = true
      text = TextFieldValue("")

      timer.cancel()
      timer = Timer()
      timer.schedule(task, 1000, 1500)
    }

    DisposableEffect(Unit) {
      onDispose {
        task.cancel()
        timer.cancel()
        isButtonEnabled = false
      }
    }
    val painter = painterResource(id = imagesStatus.data[currentImageIndex].image)
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      LazyRow() {
        items(lifes) {
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
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .weight(0.7f)
      ) {
        Image(
          painter = painter,
          contentDescription = null,
          colorFilter = ColorFilter.tint(
            Color(
              ContextCompat.getColor(
                this@GuessTheWordActivity,
                MainActivity.sharedPrefs.getColor()
              )
            )
          ),
          /*modifier = Modifier.f()*/
        )
      }
      Button(enabled = isButtonEnabled, onClick = {
        timer.cancel()
        timer = Timer()
        timer.schedule(task, 1000, 1500)
        Log.d("TAG", correctWord)
      }) {
        Row() {
          Icon(Icons.Default.Refresh, contentDescription = "content description")

          Text("Repetir")
        }
      }
      Row() {
        TextField(value = text, onValueChange = { newText ->
          text = newText
        })
        Spacer(modifier = Modifier.width(8.dp))
        FloatingActionButton(
          onClick = {
            if (text.text == correctWord) {
              correctWord = getRandomWord()
              Log.d("TAG", "Correcto")
            } else {
              lifes--
              if (lifes == 0) {
                super.onBackPressed()
              }
            }
          },
        ) {
          Icon(Icons.Default.Send, contentDescription = "content description")
        }
      }
    }
  }
}