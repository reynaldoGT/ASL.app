package com.neo.signLanguage.ui.view.activities.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimeIsUpDialog(
  onTryAgainClick: () -> Unit,
  onGoBackClick: () -> Unit,
  onDismissRequest: () -> Unit,
  onlyConfirmButton: Boolean = false,
  title: String,
  subtitle: String,
  confirmText: String,
  cancelText: String
) {
  val context = LocalContext.current

  MyMaterialTheme() {
    AlertDialog(

      onDismissRequest = {
        onDismissRequest()
      },
      title = {
        Text(
          text = title,
          fontSize = 24.sp,
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.Center,
          modifier = Modifier.fillMaxWidth()
        )
      },
      text = {
        Text(
          text = subtitle,
          fontSize = 20.sp,
          fontWeight = FontWeight.Normal,
          textAlign = TextAlign.Center,
          modifier = Modifier.fillMaxWidth()
        )
      },
      buttons = {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = if (onlyConfirmButton) Arrangement.SpaceBetween else Arrangement.End
        ) {
          if (onlyConfirmButton) {
            Button(
              onClick = onGoBackClick,
              modifier = Modifier.padding(8.dp),
              colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFBBD4D8),
                contentColor = Color.White
              )
            ) {
              Text(text = cancelText)
            }
          }

          Button(
            onClick = onTryAgainClick,
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.buttonColors(
              backgroundColor = Color(0xFF8AC6D0),
              contentColor = Color.White
            )
          ) {
            Text(text = confirmText)
          }
        }
      },
      shape = RoundedCornerShape(16.dp)
    )
  }
}