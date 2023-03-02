package com.neo.signLanguage.ui.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import com.neo.signLanguage.utils.AdUtils
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
    var isButtonEnabled by remember { mutableStateOf(true) }
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
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
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
          .height(200.dp)
      ) {
        Image(
          painter = painter,
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
      /*Row() {
        TextField(
          label = { Text(text = getStringByIdName(this@GuessTheWordActivity, "guess_the_word")) },
          placeholder = { Text(text = "¿Cual es la palabra?") },
          colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent
          ),
          value = text, onValueChange = { newText ->
            text = newText
          })
        Spacer(modifier = Modifier.width(4.dp))
        FloatingActionButton(
          backgroundColor = MaterialTheme.colors.primary,
          onClick = {
            hideKeyboard(this@GuessTheWordActivity)
            if (text.text == correctWord) {
              currentRecord++
              correctWord = getRandomWord()
              showSnackBarToGames(
                getString(R.string.correct),
                R.color.green_dark,
                this@GuessTheWordActivity.findViewById(android.R.id.content),
                this@GuessTheWordActivity,
              )
            } else {
              showSnackBarToGames(
                getString(R.string.incorrect),
                R.color.red_dark,
                this@GuessTheWordActivity.findViewById(android.R.id.content),
                this@GuessTheWordActivity,
              )
              lifes--
              if (lifes == 0) {
                sharedPrefs.setGetGuessGameTheWordRecord(currentRecord)
                AdUtils.checkCounter(this@GuessTheWordActivity)
                super.onBackPressed()
              }
            }
          },
        ) {
          Icon(Icons.Default.Send, contentDescription = "content description", tint = Color.White)
        }
      }*/
      GridWord(correctWord)
    }
  }
}


@Composable
fun GridWord(word: MutableState<String>) {

  val focusManager = LocalFocusManager.current
  /*val wordStates = remember { mutableStateListOf(*word.toCharArray().toTypedArray()) }*/
    var wordStates = remember {
      val initialList = mutableListOf<Char>()
      word.value.forEachIndexed { index, char ->
        if (index == 0) {
          initialList.add(char)
        } else {
          initialList.add(' ')
        }
      }
      mutableStateListOf(*initialList.toTypedArray())
    }
  /*LaunchedEffect(word.value){
    val initialList = mutableListOf<Char>()
    word.value.forEachIndexed { index, char ->
      if (index == 0) {
        initialList.add(char)
      } else {
        initialList.add(' ')
      }
    }
    wordStates = mutableStateListOf(*initialList.toTypedArray())
  }*/
  LazyVerticalGrid(
    columns = GridCells.Fixed(word.value.length),
    contentPadding = PaddingValues(16.dp),
    modifier = Modifier.fillMaxSize()
  ) {
    items(word.value.length) { index ->
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(4.dp)
      ) {
        Card(
          modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
          shape = RoundedCornerShape(8.dp),
          backgroundColor = Color.LightGray
        ) {

          TextField(
            value = wordStates[index].toString(),
            onValueChange = {
              if (it.isNotEmpty()) {
                wordStates[index] = it.first()
                Log.d("TAG", "RotatingImages: ${wordStates.joinToString("")}")
                if (wordStates.joinToString("") == word.value) {
                  /*word.value = getRandomWord()*/
                  /*currentRecord++*/
                  word.value = getRandomWord()
                  /*showSnackBarToGames(
                    getStringByIdName(context, "correct"),
                    R.color.green_dark,
                    context.findViewById(android.R.id.content),
                    context,
                  )*/
                }
                /*TODO reset the main word*/
                focusManager.moveFocus(FocusDirection.Next)
              } else {
                wordStates[index] = ' '
              }
            },
            textStyle = TextStyle(
              textAlign = TextAlign.Center
            ),
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
              onNext = { focusManager.moveFocus(FocusDirection.Next) }
            )
          )
        }
      }
    }
  }
}