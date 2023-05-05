package com.neo.signLanguage.ui.view.activities.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neo.signLanguage.R
import com.neo.signLanguage.utils.SharedPreferences.isDarkTheme

@Composable
fun CardWithImage(title: String, subtitle: String, image: Int, onClick: () -> Unit) {
  val context = LocalContext.current
  val color =
    colorResource(id = if (isDarkTheme(context)) R.color.white else R.color.black)
  Card(
    shape = RoundedCornerShape(8.dp),
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp)
      .clickable {
        onClick()
      }
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.padding(8.dp),

    ) {
      Image(
        painter = painterResource(id = image),
        contentDescription = null,
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp),
        contentScale = ContentScale.Fit,
        colorFilter = ColorFilter.tint(color),
      )
      Text(
        text = title,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(vertical = 8.dp),
        style = MaterialTheme.typography.titleMedium,
      )
      Text(
        text = subtitle,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodySmall,
      )
    }
  }
}