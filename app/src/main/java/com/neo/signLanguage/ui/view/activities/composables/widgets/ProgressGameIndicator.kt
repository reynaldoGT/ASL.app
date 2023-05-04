package com.neo.signLanguage.ui.view.activities.composables.widgets

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier



@Composable
fun ProgressGameIndicator(
  maxSteps: Int,
  currentStep: Int,
  onStepClick: () -> Unit,
  onProgressComplete: () -> Unit,
  modifier: Modifier
) {
  val progress = remember(currentStep, maxSteps) {
    currentStep.toFloat() / maxSteps.toFloat()
  }
  val progressAnimation by animateFloatAsState(
    targetValue = currentStep.toFloat() / maxSteps.toFloat(),
    animationSpec = tween(durationMillis = 750, easing = FastOutSlowInEasing)
  )

  LaunchedEffect(key1 = progress) {
    if (progress >= 1.0f) {
      onProgressComplete()
    }
  }

  LinearProgressIndicator(
    progress = progressAnimation,
    modifier = modifier
  )

}