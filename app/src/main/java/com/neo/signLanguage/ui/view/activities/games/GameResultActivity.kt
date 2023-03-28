package com.neo.signLanguage.ui.view.activities.games

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.ui.graphics.Color
import com.neo.signLanguage.databinding.ActivityGameResultBinding
import com.neo.signLanguage.ui.view.activities.MainActivity
import com.neo.signLanguage.ui.view.activities.composables.MyMaterialTheme
import com.neo.signLanguage.ui.view.activities.composables.widgets.DialogGameResult
import com.neo.signLanguage.utils.DialogGameDC
import com.neo.signLanguage.utils.GameResult

class GameResultActivity : AppCompatActivity() {
  private lateinit var binding: ActivityGameResultBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityGameResultBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val dialogGameDC: DialogGameDC = intent.getParcelableExtra("dialogGameDC")!!

    binding.gameResultComposeView.setContent {
      MyMaterialTheme(content = {
        DialogGameResult(
          this,
          dialogGameDC = dialogGameDC,
          setColorByResult(dialogGameDC.gameResult),
          onClick = {
            var contadorBotonAtras = 2

            if (contadorBotonAtras > 2) {
              for (i in 1..contadorBotonAtras) {
                onBackPressed()
              }
            } else {
              onBackPressed()
            }
          })
      })
    }
  }

  private fun setColorByResult(gameResult: GameResult): Color {
    return when (gameResult) {
      GameResult.WIN -> Color.Green
      GameResult.LOSE -> Color.Red
      GameResult.TIME_IS_UP -> Color.Yellow
    }
  }
}