package com.neo.signLanguage.ui.view.activities.composables

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neo.signLanguage.R
import com.neo.signLanguage.utils.SharedPreferences.isDarkTheme

@Composable
fun CardWithImage(context: Context, title: String, subtitle: String, image: Int, onClick: () -> Unit) {
  val color =
    colorResource(id = if (isDarkTheme(context)) R.color.white else R.color.black)
  Card(
    shape = RoundedCornerShape(8.dp),
    elevation = 4.dp,
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp)
      .clickable {
        onClick()
      }
  ) {
    Column(
      modifier = Modifier.padding(16.dp)
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
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 8.dp)
      )
      Text(
        text = subtitle,
        fontSize = 13.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 8.dp)
      )
    }
  }
}