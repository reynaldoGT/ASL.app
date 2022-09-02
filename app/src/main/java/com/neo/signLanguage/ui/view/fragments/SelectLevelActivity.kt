package com.neo.signLanguage.ui.view.fragments

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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.ColorAdapter
import com.neo.signLanguage.databinding.ActivitySelectLevelBinding

import com.neo.signLanguage.ui.view.activities.FindTheLetterGameActivity


class SelectLevelActivity : AppCompatActivity() {

  private lateinit var binding: ActivitySelectLevelBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySelectLevelBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.greeting.setContent {
      CustomButtonSelectLevel()
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
    Text(text = buttonTitle)
  }
}

@Composable
fun CustomButtonSelectLevel() {
  Column(
    modifier = Modifier
      .padding(16.dp)
      .fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    CustomButton(buttonTitle = "Level 1", difficulty = "easy")
    CustomButton(buttonTitle = "Level 2", difficulty = "medium")
    CustomButton(buttonTitle = "Level 3", difficulty = "hard")
  }
}


@Preview
@Composable
fun previewText() {
  CustomButtonSelectLevel()
}

