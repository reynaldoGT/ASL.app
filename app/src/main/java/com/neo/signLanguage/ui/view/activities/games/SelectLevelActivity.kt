package com.neo.signLanguage.ui.view.activities.games


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neo.signLanguage.databinding.ActivitySelectLevelBinding

import com.neo.signLanguage.ui.view.activities.composables.backIcon
import com.neo.signLanguage.utils.SharedPreferences.isDarkTheme
import com.neo.signLanguage.utils.Utils.Companion.getStringByIdName
import com.neo.signLanguage.utils.Utils.Companion.setColorByDifficulty
import java.util.*


class SelectLevelActivity : AppCompatActivity() {

  private lateinit var binding: ActivitySelectLevelBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySelectLevelBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val activityClass = intent.getSerializableExtra("activityClass") as Class<*>
    val gameName = intent.getStringExtra("gameName")

    binding.composeViewActivitySelectLevel.setContent {
      CustomButtonSelectLevel(
        onBackPressed = { onBackPressed() },
        activityClass,
        gameName = gameName!!
      )
    }
  }
}

@Composable
fun CustomButtonSelectLevel(
  onBackPressed: () -> Unit,
  activity: Class<*>,
  gameName: String
) {
  val context = LocalContext.current
  Row() {
    Column(
      modifier = Modifier.fillMaxWidth(),
      verticalArrangement = Arrangement.SpaceEvenly,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Box(
        modifier = Modifier
          .align(Alignment.Start)
          .padding(16.dp)
      ) {
        backIcon(
          onClick = {
            onBackPressed.invoke()
          }
        )
      }
      Box(
        modifier = Modifier
          .fillMaxSize()
      ) {
        Column(
          modifier = Modifier.align(Alignment.Center),
        ) {
          Text(
            text = gameName.uppercase(Locale.getDefault()),
            modifier = Modifier
              .padding(vertical = 5.dp)
              .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.h3 + TextStyle(
              textAlign = TextAlign.Center,
              fontSize = 32.sp,
              fontWeight = FontWeight.Bold
            ),
            color = if (isDarkTheme(context)) Color.White else Color.DarkGray
          )
          Text(
            text = getStringByIdName(context, "select_level").uppercase(Locale.getDefault()),
            modifier = Modifier
              .padding(10.dp)
              .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.h4 + TextStyle(
              textAlign = TextAlign.Center,
              fontSize = 24.sp,
              fontWeight = FontWeight.Bold
            ),
            color = if (isDarkTheme(context)) Color.White else Color.DarkGray
          )

          CustomButton(
            buttonTitle = getStringByIdName(context, "easy"),
            difficulty = Difficulty.EASY,
            activity
          )
          CustomButton(
            buttonTitle = getStringByIdName(context, "medium"),
            difficulty = Difficulty.MEDIUM,
            activity
          )
          CustomButton(
            buttonTitle = getStringByIdName(context, "hard"),
            difficulty = Difficulty.HARD,
            activity
          )
          CustomButton(
            buttonTitle = getStringByIdName(context, "very_hard"),
            difficulty = Difficulty.VERY_HARD,
            activity
          )
        }
      }
    }
  }
}

@Composable
fun CustomButton(buttonTitle: String, difficulty: Difficulty, activity: Class<*>) {
  val context = LocalContext.current
  Button(
    colors = ButtonDefaults.buttonColors(
      backgroundColor = setColorByDifficulty(difficulty),
      contentColor = Color.White
    ),
    modifier = Modifier
      .height(100.dp)
      .fillMaxWidth()
      .padding(10.dp),
    onClick = {
      val intent = Intent(context, activity)
      intent.putExtra("difficulty", difficulty)
      context.startActivity(intent)
    }, shape = RoundedCornerShape(100.dp)
  ) {
    Text(text = buttonTitle, modifier = Modifier.padding(10.dp), fontSize = 20.sp)
  }
}




