package com.neo.signLanguage.ui.view.activities.composables

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.neo.signLanguage.ui.view.activities.MainActivity.Companion.sharedPrefs

@Composable
fun MyMaterialTheme(
  content: @Composable () -> Unit
) {

  val typography = Typography(
    // Establecer el color de texto predeterminado en blanco
    body1 = TextStyle(color = if (sharedPrefs.isDarkTheme()) Color.White else Color.Black),
    // Agregar otros estilos de texto aquí si es necesario
  )

  val colors = if (sharedPrefs.isDarkTheme()) darkColors() else lightColors()
  MaterialTheme(colors = colors, content = content, typography = typography)
}