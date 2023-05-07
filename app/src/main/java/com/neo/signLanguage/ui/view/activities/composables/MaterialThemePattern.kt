package com.neo.signLanguage.ui.view.activities.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import com.neo.signLanguage.R

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.neo.signLanguage.utils.SharedPreferences.isDarkTheme

@Composable
fun MyMaterialTheme(
  content: @Composable () -> Unit
) {
  val context = LocalContext.current
  val colorText = TextStyle(color = if (isDarkTheme(context)) Color.White else Color.Black)
  val typography = Typography(
    // Establecer el color de texto predeterminado en blanco
    bodyMedium = colorText,
    titleMedium = colorText,
    // Agregar otros estilos de texto aquí si es necesario
  )

  /*val colors = if (isDarkTheme(context)) ColorScheme.dark else ColorScheme.light*/
  MaterialTheme(
    content = content, typography = typography,
    colorScheme = if (isDarkTheme(context)) darkColorScheme() else lightColorScheme(
      primary = colorResource(id = R.color.primaryColor),
      /*= colorResource(id = R.color.primaryColorVariant),*/
      onPrimary = colorResource(id = R.color.primaryLightColor),
    )
  )
}

@Composable
fun backIcon(onClick: () -> Unit) {
  IconButton(
    onClick = { onClick() },
    modifier = Modifier
      .background(MaterialTheme.colorScheme.primary, CircleShape)
      .padding(4.dp)
      .size(40.dp)
  ) {
    Icon(Icons.Default.ArrowBack, contentDescription = "Botón de retroceso", tint = Color.White)
  }
}