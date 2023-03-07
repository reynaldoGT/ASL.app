package com.neo.signLanguage.ui.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.ActivityGuessTheWordBinding
import com.neo.signLanguage.ui.view.activities.composables.MyMaterialTheme
import com.neo.signLanguage.ui.view.activities.MainActivity.Companion.sharedPrefs
import com.neo.signLanguage.ui.view.activities.composables.backIcon
import com.neo.signLanguage.utils.DataSign.Companion.generateListImageSign
import com.neo.signLanguage.utils.Utils.Companion.getRandomWord
import com.neo.signLanguage.utils.Utils.Companion.getStringByIdName
import com.neo.signLanguage.utils.Utils.Companion.hideKeyboard
import com.neo.signLanguage.utils.Utils.Companion.showSnackBarToGames


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
    var correctWord = remember { mutableStateOf(getRandomWord()) }
    var imagesStatus by remember { mutableStateOf(generateListImageSign(correctWord.value)) }
    var currentImageIndex by remember { mutableStateOf(0) }
    var lifes by remember { mutableStateOf(3) }
    var isButtonEnabled by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var currentRecord by remember { mutableStateOf(0) }
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
      isButtonEnabled = true
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
          modifier = Modifier.height(350.dp),
          contentDescription = null,
          colorFilter = ColorFilter.tint(
            Color(
              ContextCompat.getColor(
                this@GuessTheWordActivity,
                sharedPrefs.getColor()
              )
            )
          ),
        )
      }
      Button(enabled = isButtonEnabled, onClick = {
        Log.d("TAG", "RotatingImages: ${correctWord.value}")
        hideKeyboard(this@GuessTheWordActivity)
        timer.cancel()
        timer = Timer()
        timer.schedule(task, 1000, 1500)
      }) {
        Row() {
          Icon(
            Icons.Default.Refresh,
            contentDescription = "content description",
            tint = Color.White
          )
          Text(text = getStringByIdName(this@GuessTheWordActivity, "repeat"))
        }
      }
      GridWord(correctWord, this@GuessTheWordActivity)
    }
  }
}


@Composable
fun GridWord(word: MutableState<String>, view: GuessTheWordActivity) {
  val context = LocalContext.current

  val focusManager = LocalFocusManager.current
  /*val wordStates = remember { mutableStateListOf(*word.toCharArray().toTypedArray()) }*/
  val textFieldsState = mutableStateListOf(*Array(word.value.length) { ' ' })

  /*fun onTextFieldValueChanged(index: Int, value: Char) {
    words
  }*/

// En la función que verifica si se completaron los textfields
  if (textFieldsState.joinToString("") == word.value) {
    word.value = getRandomWord()

  }

// En el compositor
  var wordStates by remember { mutableStateOf(mutableListOf(*Array(word.value.length) { ' ' })) }

  LaunchedEffect(word.value) {
    val initialList = mutableListOf<Char>()
    word.value.forEachIndexed { index, char ->
      if (index == 0) {
        initialList.add(char)
      } else {
        initialList.add(' ')
      }
    }
    wordStates = mutableStateListOf(*initialList.toTypedArray())
  }
  LazyRow(
    modifier = Modifier
      .wrapContentWidth()
      .padding(8.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    items(wordStates.size) { index ->
      Box(
        modifier = Modifier
          .fillMaxSize(),
      ) {
        Card(
          modifier = Modifier
            .width(50.dp)
            .height(50.dp),
          /*.padding(4.dp),*/
          shape = RoundedCornerShape(4.dp),
          backgroundColor = Color.LightGray
        ) {
          TextField(
            value = wordStates[index].toString(),
            onValueChange = {
              if (it.isNotEmpty()) {
                wordStates.set(index, it[0])
                Log.d("TAG", "RotatingImages: ${wordStates.joinToString("")}")
                if (wordStates.joinToString("") == word.value) {
                  word.value = getRandomWord()
                  /*currentRecord++*/
                  showSnackBarToGames(
                    getStringByIdName(view, "correct"),
                    R.color.green_dark,
                    view.findViewById(android.R.id.content),
                    context,
                  )
                }
                /*TODO reset the main word*/
                focusManager.moveFocus(FocusDirection.Next)
              } else {
                wordStates[index] = ' '
              }
            },
            textStyle = TextStyle(
              textAlign = TextAlign.Center,
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp
            ),
            maxLines = 1,
            singleLine = true,
            /*keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),*/
            keyboardOptions = (KeyboardOptions.Default.copy(imeAction = ImeAction.Next)),
            keyboardActions = KeyboardActions(
              onNext = { focusManager.moveFocus(FocusDirection.Next) }
            )

          )
        }
      }
    }
  }
}