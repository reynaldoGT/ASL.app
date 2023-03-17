package com.neo.signLanguage.ui.view.activities.games


import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.ActivityCardMatchingWithArrowsBinding
import com.neo.signLanguage.ui.view.activities.composables.MyMaterialTheme
import com.neo.signLanguage.ui.view.activities.composables.TimeIsUpDialog
import com.neo.signLanguage.ui.view.activities.composables.backIcon
import com.neo.signLanguage.ui.view.activities.composables.widgets.LivesCounter
import com.neo.signLanguage.ui.view.activities.composables.widgets.ProgressGameIndicator
import com.neo.signLanguage.utils.GamesUtils
import com.neo.signLanguage.utils.GamesUtils.Companion.generateOptionsToQuiz
import com.neo.signLanguage.utils.SharedPreferences
import com.neo.signLanguage.utils.SharedPreferences.getVibration
import com.neo.signLanguage.utils.Utils
import com.neo.signLanguage.utils.Utils.Companion.playCorrectSound


class CardMatchingWithArrowsActivity : AppCompatActivity() {
  private lateinit var binding: ActivityCardMatchingWithArrowsBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityCardMatchingWithArrowsBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.composeViewCardMatchingWithArrows.setContent {
      MyMaterialTheme(
        content = {
          CardMatchingWithArrowsContent(onClick = { onBackPressed() }, onBack = { onBackPressed() })
        }
      )
    }
  }

  @Composable
  fun CardMatchingWithArrowsContent(onClick: () -> Unit, onBack: () -> Unit) {
    val generateOptionsToQuiz = remember { mutableStateOf(generateOptionsToQuiz()) }
    var currentStep by remember { mutableStateOf(0) }
    var maxSteps by remember { mutableStateOf(5) }
    var lifesAmount by remember { mutableStateOf(5) }
    var selectedIndex by remember { mutableStateOf(-1) }
    var optionSelected by remember { mutableStateOf("") }
    var showDialogWithoutIntents by remember { mutableStateOf(false) }
    var showDialogLevelCompleted by remember { mutableStateOf(false) }

    val mediaPlayer = remember {
      MediaPlayer.create(this@CardMatchingWithArrowsActivity, R.raw.correct2_sound)
    }
    DisposableEffect(mediaPlayer) {
      onDispose {
        mediaPlayer.release()
      }
    }
    fun verifyAnswer() {
      /*Reset the selected item*/
      selectedIndex = -1
      if (optionSelected == generateOptionsToQuiz.value.correctAnswer) {
        /*Play sound*/
        /*mediaPlayer.start()*/
        playCorrectSound(this@CardMatchingWithArrowsActivity, mediaPlayer)
        /*Select the options selected*/

        optionSelected = ""
        generateOptionsToQuiz.value = generateOptionsToQuiz()

        if (currentStep < maxSteps) {
          currentStep++
        } else {
          currentStep = 0
        }
      } else {
        lifesAmount--
        Utils.vibratePhone(applicationContext)
        if (lifesAmount == 0) {
          /*onClick()*/
          showDialogWithoutIntents = true
        }
      }
    }

    Box() {
      Column() {
        Box(
          modifier = Modifier
            .padding(8.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically
          ) {
            backIcon(
              onClick = {
                onBack()
              }
            )

            ProgressGameIndicator(
              maxSteps = maxSteps,
              currentStep = currentStep,
              onStepClick = { currentStep++ },
              onProgressComplete = {
                showDialogLevelCompleted = true
              },
              modifier = Modifier
                .weight(1f)
                .height(16.dp)
                .padding(horizontal = 8.dp)
                .clip(RoundedCornerShape(20.dp))
            )
            LivesCounter(lifesAmount)
          }

        }
        /*TODO change the strings*/
        if (showDialogWithoutIntents) {
          TimeIsUpDialog(
            onTryAgainClick = {
              onClick()
            },
            onGoBackClick = {

              onBack()
            },
            onDismissRequest = {
              showDialogWithoutIntents = false
            },
            false,
            Utils.getStringByIdName(LocalContext.current, "level_completed"),
            Utils.getStringByIdName(LocalContext.current, "try_next_level"),
            Utils.getStringByIdName(LocalContext.current, "go_back"),
            Utils.getStringByIdName(LocalContext.current, "go_back"),
            )
        }
        if (showDialogLevelCompleted) {
          TimeIsUpDialog(
            onTryAgainClick = {
              onClick()
            },
            onGoBackClick = {
              onBack()
            },
            onDismissRequest = {
              showDialogWithoutIntents = false
            },
            false,
            Utils.getStringByIdName(LocalContext.current, "level_completed"),
            Utils.getStringByIdName(LocalContext.current, "try_next_level"),
            Utils.getStringByIdName(LocalContext.current, "go_back"),
            Utils.getStringByIdName(LocalContext.current, "go_back"),

            )
        }
        Text(
          text = "¿Que dice la palabra?",
          modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
          textAlign = TextAlign.Center,
          fontSize = 22.sp,
          fontWeight = FontWeight.W600
        )
        LazyVerticalGrid(
          modifier = Modifier.padding(vertical = 16.dp),
          columns = GridCells.Fixed(generateOptionsToQuiz.value.signList.size),
          /*modifier = Modifier.align(Alignment.Center),*/
          content = {
            items(generateOptionsToQuiz.value.signList.size) { index ->
              Card(
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                  .fillMaxSize()
                  .padding(8.dp)
              ) {
                Image(
                  painter = painterResource(id = generateOptionsToQuiz.value.signList[index].image),
                  contentDescription = null,
                  colorFilter = Utils.getHandColor(this@CardMatchingWithArrowsActivity),
                  modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
                    .padding(8.dp)
                )
              }
            }
          }
        )

        LazyVerticalGrid(
          columns = GridCells.Fixed(2),
          /*modifier = Modifier.align(Alignment.Center),*/
          content = {
            items(generateOptionsToQuiz.value.options.size) { index ->
              Card(
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                  .fillMaxSize()
                  .padding(8.dp)
                  .border(
                    width = 4.dp,
                    color = if (selectedIndex == index) Color.Green else Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                  )
                  .clickable {
                    selectedIndex = index
                    optionSelected = generateOptionsToQuiz.value.options[index]

                  }
              ) {
                Text(
                  text = generateOptionsToQuiz.value.options[index],
                  style = MaterialTheme.typography.h6,
                  textAlign = TextAlign.Center,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 40.dp, horizontal = 16.dp)
                )
              }
            }
          }
        )
        Spacer(modifier = Modifier.weight(1f, true))
        Button(
          enabled = optionSelected.isNotEmpty(),
          colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
          ),
          modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
          onClick = {
            verifyAnswer()
          }, shape = RoundedCornerShape(100.dp)
        ) {
          Text(
            text = "Comprobar",
            modifier = Modifier.padding(vertical = 10.dp),
            fontSize = 16.sp,
            color = Color.White
          )
        }
      }
    }
  }
}