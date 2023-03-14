package com.neo.signLanguage.ui.view.activities.composables


import androidx.compose.animation.core.*

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun TimerScreen(
  onTimerEnd: () -> Unit,
  timeInSeconds: Int = 30,
  color: Color = Color.Blue,
  timerActive: MutableState<Boolean>,
  timerPaused: MutableState<Boolean>
) {
  var remainingTimeSeconds by remember { mutableStateOf(timeInSeconds) }
  val progressAnimDuration = 1500
  val progressAnimation by animateFloatAsState(
    targetValue = remainingTimeSeconds.toFloat() / timeInSeconds.toFloat(),
    animationSpec = tween(durationMillis = progressAnimDuration, easing = FastOutSlowInEasing)
  )

  suspend fun runTimer() {
    if (timerActive.value) {
      remainingTimeSeconds = timeInSeconds + 1
      while (remainingTimeSeconds > 0) {
        if (!timerPaused.value) {
          delay(1000)
          remainingTimeSeconds -= 1
          if (remainingTimeSeconds == 0) {
            timerActive.value = false
            onTimerEnd()
          }
        } else {
          // If timer is paused, don't decrement remainingTimeSeconds
          delay(100)
        }
      }
    }
  }

  LaunchedEffect(timerActive.value) {
    runTimer()
  }

  LaunchedEffect(Unit) {
    runTimer()
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