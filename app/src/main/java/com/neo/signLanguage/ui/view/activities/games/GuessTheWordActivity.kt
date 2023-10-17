package com.neo.signLanguage.ui.view.activities.games

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.OutlinedTextField

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.ActivityGuessTheWordBinding
import com.neo.signLanguage.ui.view.activities.composables.MyMaterialTheme
import com.neo.signLanguage.ui.view.activities.composables.backIcon
import com.neo.signLanguage.utils.DataSign.Companion.generateListImageSign
import com.neo.signLanguage.utils.GamesUtils.Companion.getRandomWord
import com.neo.signLanguage.utils.Utils
import com.neo.signLanguage.utils.Utils.Companion.getHandColor
import com.neo.signLanguage.utils.Utils.Companion.getStringByIdName
import com.neo.signLanguage.utils.Utils.Companion.hideKeyboard
import com.neo.signLanguage.utils.Utils.Companion.showSnackBarToGames
import com.neo.signLanguage.utils.processWord


import java.util.*


class GuessTheWordActivity : AppCompatActivity() {
  private lateinit var binding: ActivityGuessTheWordBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityGuessTheWordBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.ActivityGuessTheWordBindingComposeView.setContent {
      MyMaterialTheme(content = {
        RotatingImages(onClick = { onBackPressed() })
      })
    }
  }

  @Composable
  fun RotatingImages(onClick: () -> Unit) {
    val correctWord = remember { mutableStateOf(getRandomWord()) }
    var imagesStatus by remember { mutableStateOf(generateListImageSign(correctWord.value)) }
    var currentImageIndex by remember { mutableStateOf(0) }
    var isButtonEnabled by remember { mutableStateOf(false) }
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

    LaunchedEffect(correctWord.value) {
      imagesStatus = generateListImageSign(correctWord.value)
      currentImageIndex = 0
      isButtonEnabled = false
      text = TextFieldValue("")

      timer.cancel()
      timer = Timer()
      timer.schedule(task, 2000, 1500)
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
      modifier = Modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
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
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
      ) {
        Image(
          painter = painter,
          modifier = Modifier
            .height(350.dp)
            .align(alignment = Alignment.CenterHorizontally),
          contentDescription = null,
          colorFilter = getHandColor(this@GuessTheWordActivity),
        )
      }
      Button(enabled = isButtonEnabled,
        shape = RoundedCornerShape(40.dp),
        onClick = {
          isButtonEnabled = false
          hideKeyboard(this@GuessTheWordActivity)
          timer.cancel()
          timer = Timer()
          timer.schedule(task, 1000, 1500)
          Log.d("TAG", "correctWord.value: ${correctWord.value}")
        }) {
        Row(
          modifier = Modifier.padding(horizontal = 32.dp /*vertical = 8.dp*/),
        ) {
          Icon(
            Icons.Default.Refresh,
            contentDescription = "content description",
            tint = Color.White
          )
          Text(text = getStringByIdName(this@GuessTheWordActivity, "repeat"), color = Color.White)
        }
      }
      GridWord(correctWord.value, onCorrectWord = {
        hideKeyboard(this@GuessTheWordActivity)
        Utils.showSnackBar(
          this@GuessTheWordActivity,
          R.string.correct,
        )
        correctWord.value = getRandomWord()
      })
    }
  }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GridWord(word: String, onCorrectWord: () -> Unit) {
  val context = LocalContext.current
  var text by remember { mutableStateOf("") }

  // En la función que verifica si se completaron los textfields

  Box(
    modifier = Modifier
      .wrapContentWidth()
      .padding(8.dp),
  ) {
    OutlinedTextField(
      maxLines = 1,
      value = text,
      textStyle = MaterialTheme.typography.displayLarge.copy(
        textAlign = TextAlign.Center
      ),

      onValueChange = {
        text = it.lowercase()
        Log.d("TAG", "text: $text")
        if (it == word) {
          text = ""
          onCorrectWord()
        }
      },
      label = {
        Text(processWord(word), style = MaterialTheme.typography.titleLarge.copy(
          letterSpacing = 5.sp,
        ))
      },
      supportingText = {
        Text(
          text = "${text.length} / ${word.length}",
          modifier = Modifier.fillMaxWidth(),
          textAlign = TextAlign.End,
        )
      },
    )
  }
}