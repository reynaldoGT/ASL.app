package com.neo.signLanguage.ui.view.fragments

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getColor
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.ColorAdapter
import com.neo.signLanguage.databinding.ActivitySelectLevelBinding

import com.neo.signLanguage.ui.view.activities.FindTheLetterGameActivity
import com.neo.signLanguage.utils.Utils.Companion.getStringByIdName


class SelectLevelActivity : AppCompatActivity() {

  private lateinit var binding: ActivitySelectLevelBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySelectLevelBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.greeting.setContent {
      CustomButtonSelectLevel()

      /*binding.btnLevel1.setOnClickListener {
          goToGame("easy")
      }
      binding.btnLevel2.setOnClickListener {
          goToGame("medium")
      }
      binding.btnLevel3.setOnClickListener {
          goToGame("hard")
      }*/

    }
  }
}

@Composable
fun CustomButton(buttonTitle: String, difficulty: String) {
  val context = LocalContext.current
  Button(
    modifier = Modifier
      .height(100.dp)
      .fillMaxWidth()
      .padding(10.dp),
    onClick = {
      val intent = Intent(context, FindTheLetterGameActivity::class.java)
      intent.putExtra("difficulty", difficulty);
      context.startActivity(intent)
    }, shape = RoundedCornerShape(100.dp)
  ) {
    Text(text = buttonTitle, modifier = Modifier.padding(10.dp), fontSize = 20.sp)
  }
}

@Composable
fun CustomButtonSelectLevel() {
  val context = LocalContext.current
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.SpaceEvenly,
    horizontalAlignment = Alignment.CenterHorizontally,

  ) {
    Text(
      text = getStringByIdName(context, "select_level"),
      modifier = Modifier.padding(10.dp),
      style = MaterialTheme.typography.h3

    )

    CustomButton(buttonTitle = getStringByIdName(context, "easy"), difficulty = "easy")
    CustomButton(buttonTitle = getStringByIdName(context, "medium"), difficulty = "medium")
    CustomButton(buttonTitle = "Hard", difficulty = "hard")
  }
}


@Preview
@Composable
fun previewText() {
  CustomButtonSelectLevel()
}

