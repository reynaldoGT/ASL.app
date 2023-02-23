import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class GuessResult {
  CORRECT, INCORRECT
}

@Composable
fun GuessResultMessage(
  result: GuessResult,
  onDismiss: () -> Unit
) {
  val message = when (result) {
    GuessResult.CORRECT -> "¡Felicidades, acertaste!"
    GuessResult.INCORRECT -> "Lo siento, fallaste. Inténtalo de nuevo."
  }

  val color = when (result) {
    GuessResult.CORRECT -> Color.Green
    GuessResult.INCORRECT -> Color.Red
  }

  Surface(
    color = color.copy(alpha = 0.9f),
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp)
      .clip(RoundedCornerShape(16.dp)),

    elevation = 8.dp
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
    ) {
      Text(
        text = message,
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
      )
      Spacer(modifier = Modifier.height(16.dp))
      Button(
        onClick = { onDismiss() },
        colors = ButtonDefaults.buttonColors(
          backgroundColor = Color.White,
          contentColor = color
        ),
        modifier = Modifier.align(Alignment.End)
      ) {
        Text("Cerrar")
      }
    }
  }
}