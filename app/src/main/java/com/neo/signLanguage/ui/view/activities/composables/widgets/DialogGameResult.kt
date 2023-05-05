package com.neo.signLanguage.ui.view.activities.composables.widgets

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neo.signLanguage.utils.Utils
import com.neo.signLanguage.utils.DialogGameDC


@Composable
fun DialogGameResult(
  context: Context,
  dialogGameDC: DialogGameDC,
  color: Color,
  onClick: () -> Unit
) {
  val (title, subtitle, audio, image, buttonText) = dialogGameDC
  val sound = remember {
    MediaPlayer.create(context, audio)
  }
  DisposableEffect(sound) {
    onDispose {
      sound.release()
    }
  }
  Utils.playCorrectSound(context, sound)
  Box() {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Image(
        painter = painterResource(id = image),
        contentDescription = null,
        colorFilter = ColorFilter.tint(
          color
        ),
        modifier = Modifier
          .padding(8.dp)
          .width(175.dp)
          .height(175.dp)
      )
      Text(
        text = title,
        modifier = Modifier.padding(top = 32.dp),
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center
      )
      Text(
        text = subtitle,
        modifier = Modifier.padding(16.dp, 24.dp),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge,
      )
      ButtonAction(
        color = color,
        text = buttonText,
        onClick = {
          onClick()
        }
      )
    }
  }
}

@Composable
fun ButtonAction(color: Color, text: String, onClick: () -> Unit) {
  Button(
    colors = ButtonDefaults.buttonColors(
      containerColor = color,
      contentColor = Color.White
    ),
    onClick = { onClick() },
    modifier = Modifier
      .padding(8.dp),
    shape = RoundedCornerShape(100.dp)
  ) {
    Text(
      text = text,
      fontSize = 16.sp,
      fontWeight = FontWeight.Bold,
      color = Color.White,
      modifier = Modifier.padding(70.dp, 5.dp),
      textAlign = TextAlign.Center,

      )
  }
}