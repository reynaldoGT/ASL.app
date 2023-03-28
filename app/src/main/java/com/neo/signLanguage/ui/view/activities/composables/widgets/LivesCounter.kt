package com.neo.signLanguage.ui.view.activities.composables.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LivesCounter(amountLives: Int) {
  return Box() {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(
        Icons.Outlined.Favorite,
        contentDescription = "Favorite",
        tint = Color.Red,
        modifier = Modifier
          .size(32.dp)
      )
      Text(
        text = amountLives.toString(),
        modifier = Modifier.padding(4.dp),
        color = Color.Red,
        fontSize = 22.sp,
        fontWeight = FontWeight.W600

      )
    }
  }
}