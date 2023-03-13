package com.neo.signLanguage.ui.view.activities.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.neo.signLanguage.utils.SharedPreferences.isDarkTheme

@Composable
fun MyMaterialTheme(
  content: @Composable () -> Unit
) {
  val context = LocalContext.current
  val typography = Typography(
    // Establecer el color de texto predeterminado en blanco
    body1 = TextStyle(color = if (isDarkTheme(context)) Color.White else Color.Black),
    // Agregar otros estilos de texto aquí si es necesario
  )

  val colors = if (isDarkTheme(context)) darkColors() else lightColors()
  MaterialTheme(colors = colors, content = content, typography = typography)
}

@Composable
fun backIcon(onClick: () -> Unit) {
  IconButton(
    onClick = { onClick() },
    modifier = Modifier
      .background(MaterialTheme.colors.primary, CircleShape)
      .padding(4.dp)
      .size(40.dp)
  ) {
    Icon(Icons.Default.ArrowBack, contentDescription = "Botón de retroceso", tint = Color.White)
  }
}