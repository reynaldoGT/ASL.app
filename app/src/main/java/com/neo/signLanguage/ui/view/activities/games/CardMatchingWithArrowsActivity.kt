package com.neo.signLanguage.ui.view.activities.games

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.ActivityCardMatchingWithArrowsBinding
import com.neo.signLanguage.ui.view.activities.composables.MyMaterialTheme
import com.neo.signLanguage.ui.view.activities.composables.backIcon
import com.neo.signLanguage.ui.view.activities.composables.widgets.LivesCounter
import com.neo.signLanguage.ui.view.activities.composables.widgets.ProgressGameIndicator
import com.neo.signLanguage.utils.*
import com.neo.signLanguage.utils.AdUtils.Companion
import com.neo.signLanguage.utils.GamesUtils.Companion.generateOptionsToQuiz
import com.neo.signLanguage.utils.Utils.Companion.getStringByIdName
import com.neo.signLanguage.utils.Utils.Companion.playCorrectSound


class CardMatchingWithArrowsActivity : AppCompatActivity() {
  private lateinit var binding: ActivityCardMatchingWithArrowsBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // Inicializa los anuncios
    AdUtils.initAds(this)
    AdUtils.initListeners()

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
    val playLooseRound = remember {
      MediaPlayer.create(this@CardMatchingWithArrowsActivity, R.raw.lose_sound2)
    }
    val mediaPlayerWin = remember {
      MediaPlayer.create(this@CardMatchingWithArrowsActivity, R.raw.win_sound)
    }
    val wrongSound = remember {
      MediaPlayer.create(this@CardMatchingWithArrowsActivity, R.raw.wrong_sound)
    }
    DisposableEffect(mediaPlayer) {
      onDispose {
        mediaPlayer.release()
        mediaPlayerWin.release()
        playLooseRound.release()
        wrongSound.release()
      }
    }
    fun verifyAnswer() {
      /*Reset the selected item*/
      selectedIndex = -1

      if (optionSelected == generateOptionsToQuiz.value.correctAnswer) {
        optionSelected = ""
        /*Play sound*/
        playCorrectSound(this@CardMatchingWithArrowsActivity, mediaPlayer)
        /*Select the options selected*/
        generateOptionsToQuiz.value = generateOptionsToQuiz()

        if (currentStep < maxSteps) {
          currentStep++
        } else {
          currentStep = 0
        }
      } else {
        /*Play sound*/
        optionSelected = ""
        playCorrectSound(this@CardMatchingWithArrowsActivity, wrongSound)
        lifesAmount--
        Utils.vibratePhone(applicationContext)
        if (lifesAmount == 0) {
          val dialogGameDC = DialogGameDC(
            getStringByIdName(this@CardMatchingWithArrowsActivity, "you_lost"),
            getStringByIdName(this@CardMatchingWithArrowsActivity, "better_luck"),
            R.raw.lose_sound2,
            getLoseIcons(),
            getStringByIdName(this@CardMatchingWithArrowsActivity, "return_menu"),
            GameResult.LOSE,
          )
          goResultActivity(this@CardMatchingWithArrowsActivity, dialogGameDC)
          AdUtils.checkAdCounter(this@CardMatchingWithArrowsActivity)
        }
      }
    }

    Box() {
      Column() {
        Box(
          modifier = Modifier.padding(8.dp)
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
                val dialogGameDC = DialogGameDC(
                  title = getStringByIdName(
                    this@CardMatchingWithArrowsActivity,
                    "level_completed"
                  ),
                  subtitle = getStringByIdName(
                    this@CardMatchingWithArrowsActivity,
                    "go_to_the_next_level"
                  ),
                  audio = R.raw.win_sound,
                  getWinIcons(),
                  buttonText = getStringByIdName(
                    this@CardMatchingWithArrowsActivity,
                    "go_to_next_level"
                  ),
                  GameResult.WIN
                )
                goResultActivity(this@CardMatchingWithArrowsActivity, dialogGameDC)
                AdUtils.checkAdCounter(this@CardMatchingWithArrowsActivity)
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
        Text(
          text = getStringByIdName(this@CardMatchingWithArrowsActivity, "what_does_the_word_say"),
          modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
          textAlign = TextAlign.Center,
          style = MaterialTheme.typography.titleLarge
        )
        LazyVerticalGrid(
          modifier = Modifier.padding(vertical = 8.dp),
          columns = GridCells.Fixed(generateOptionsToQuiz.value.signList.size),
          contentPadding = PaddingValues(0.dp),
          verticalArrangement = Arrangement.Center,

          content = {
            val numItems = generateOptionsToQuiz.value.signList.size
            val padding = if (numItems > 5) {
              4.dp
            } else {
              (8 - numItems) * 2.dp
            }
            items(generateOptionsToQuiz.value.signList.size) { index ->
              Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                  .fillMaxSize()
                  .padding(padding)
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
                  style = MaterialTheme.typography.titleMedium,
                  textAlign = TextAlign.Center,
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