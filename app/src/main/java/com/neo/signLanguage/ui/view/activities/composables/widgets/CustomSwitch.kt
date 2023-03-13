package com.neo.signLanguage.ui.view.activities.composables.widgets

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.neo.signLanguage.ui.view.activities.MainActivity
import com.neo.signLanguage.utils.SharedPreferences.isDarkTheme
import com.neo.signLanguage.utils.Utils

@Composable
fun CustomSwitch(
  label: String,
  switchState: MutableState<Boolean>,
  onSwitchChanged: (Boolean) -> Unit
) {
  val context = LocalContext.current
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.End,
  ) {
    Text(
      text = label,
      color = if (isDarkTheme(context)) Color.White else Color.Black,
      fontWeight = FontWeight.Bold,
      textAlign = TextAlign.Center,
      modifier = Modifier.padding(8.dp),
    )
    Spacer(modifier = Modifier.width(16.dp))
    Switch(
      checked = switchState.value,
      onCheckedChange = {
        switchState.value = it
        /*MainActivity.sharedPrefs.setIsGridLayout(it)*/
        onSwitchChanged(it)
      },
      modifier = Modifier.align(Alignment.CenterVertically)
    )
  }
}

@Composable
fun CustomSwitchWithTitle(

  label: String,
  titleLabel: String,
  switchState: MutableState<Boolean>,
  onSwitchChanged: (Boolean) -> Unit,
) {
  val context = LocalContext.current
  Column() {
    Text(
      text = titleLabel,
      style = MaterialTheme.typography.h6,
      color = if (isDarkTheme(context)) Color.White else Color.Black,
    )
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.End,
    ) {
      Text(
        text = label,
        color = if (isDarkTheme(context)) Color.White else Color.Black,
        style = MaterialTheme.typography.subtitle1,
        /*modifier = Modifier.padding(top = 8.dp),*/
      )
      Spacer(modifier = Modifier.width(16.dp))
      Switch(
        checked = switchState.value,
        onCheckedChange = {
          switchState.value = it
          /*MainActivity.sharedPrefs.setIsGridLayout(it)*/
          onSwitchChanged(it)
        },
        modifier = Modifier.align(Alignment.CenterVertically)
      )
    }
  }
}