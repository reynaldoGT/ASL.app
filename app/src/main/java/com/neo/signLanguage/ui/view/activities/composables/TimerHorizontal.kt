package com.neo.signLanguage.ui.view.activities.composables

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset.Companion.Infinite
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun TimerScreen(
  onTimerEnd: () -> Unit,
  timeInSeconds: Int = 30,
  color: Color = Color.Blue
) {
  var remainingTimeSeconds by remember { mutableStateOf(timeInSeconds) }
  val progressAnimDuration = 1500
  val progressAnimation by animateFloatAsState(
    targetValue = remainingTimeSeconds.toFloat() / timeInSeconds.toFloat(),
    animationSpec = tween(durationMillis = progressAnimDuration, easing = FastOutSlowInEasing)
  )
  LaunchedEffect(Unit) {
    while (remainingTimeSeconds > 0) {
      delay(1000)
      remainingTimeSeconds -= 1
      if (remainingTimeSeconds == 0) {
        onTimerEnd()
      }
    }
  }

  Box(
    modifier = Modifier.padding(vertical = 4.dp)
  ) {
    LinearProgressIndicator(
      progress = progressAnimation,
      color = color,
      modifier = Modifier
        .fillMaxWidth()
        .height(6.dp)
        .align(Alignment.BottomCenter)
        .clip(RoundedCornerShape(20.dp))
    )
  }
}