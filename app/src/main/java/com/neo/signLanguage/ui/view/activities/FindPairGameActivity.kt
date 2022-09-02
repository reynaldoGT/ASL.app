package com.neo.signLanguage.ui.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.GridLayoutManager
import com.neo.signLanguage.adapters.AdapterLettersGameRotate
import com.neo.signLanguage.adapters.ClickListener
import com.neo.signLanguage.data.models.Sign
import com.neo.signLanguage.databinding.ActivityFindPairGameBinding
import com.neo.signLanguage.ui.view.fragments.CustomButton
import com.neo.signLanguage.ui.view.fragments.CustomButtonSelectLevel

import com.neo.signLanguage.ui.viewModel.GameViewModel
import com.neo.signLanguage.utils.Game
import com.orhanobut.logger.Logger
import kotlin.math.sign

class FindPairGameActivity : AppCompatActivity() {

  private lateinit var binding: ActivityFindPairGameBinding

  lateinit var adapter: AdapterLettersGameRotate
  private val model: GameViewModel by viewModels()

  @OptIn(ExperimentalMaterialApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityFindPairGameBinding.inflate(layoutInflater)
    model.getRandomToFindEquals(4)
    setContentView(binding.root)
    binding.composeView.setContent {
      /*val state by remember {
        mutableStateOf(CardFace.Front)
      }*/
      PhotoGrid(model.randomGameLetters.value!!)
    }
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhotoGrid(randomGameLetters: Game) {
  LazyVerticalGrid(
    columns = GridCells.Adaptive(minSize = 128.dp)
  ) {
    items(randomGameLetters.data.size) { i ->
      FlipCard(
        sign = randomGameLetters.data[i],
        modifier = Modifier
          .padding(16.dp)
          .size(100.dp),
        axis = RotationAxis.AxisY,
        back = {
          Text(
            text = "Front", Modifier
              .fillMaxSize()
              .background(Color.Red)
          )
        },
        front = {
          Text(
            text = "Back", Modifier
              .fillMaxSize()
              .background(Color.Green)
          )
        }
      )
    }
  }
}

enum class CardFace(val angle: Float) {
  Front(0f) {
    override val next: CardFace
      get() = Back
  },
  Back(180f) {
    override val next: CardFace
      get() = Front
  };

  abstract val next: CardFace
}

enum class RotationAxis {
  AxisX,
  AxisY,
}

@ExperimentalMaterialApi
@Composable
fun FlipCard(
  modifier: Modifier = Modifier,
  axis: RotationAxis = RotationAxis.AxisY,
  back: @Composable () -> Unit = {},
  front: @Composable () -> Unit = {},
  sign: Sign
) {
  var stateRotation by remember {
    mutableStateOf(CardFace.Front)
  }
  val rotation = animateFloatAsState(
    targetValue = stateRotation.angle,
    animationSpec = tween(
      durationMillis = 400,
      easing = FastOutSlowInEasing,
    )
  )
  AnimatedVisibility(
    visible = stateRotation == CardFace.Front,
    enter = fadeIn(),
    exit = fadeOut()
  ) {
    Card(
      onClick = {
        stateRotation = stateRotation.next
      },
      modifier = modifier
        .graphicsLayer {
          if (axis == RotationAxis.AxisX) {
            rotationX = rotation.value
          } else {
            rotationY = rotation.value
          }
          cameraDistance = 12f * density
        },
    ) {
      if (rotation.value <= 90f) {
        Box(
          Modifier.fillMaxSize()
        ) {
          front()
        }
      } else {
        Box(
          Modifier
            .fillMaxSize()
            .graphicsLayer {
              if (axis == RotationAxis.AxisX) {
                rotationX = 180f
              } else {
                rotationY = 180f
              }
            },
        ) {
          Image(
            painterResource(sign.image),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
          )
        }
      }
    }
  }
  /*Card(
    onClick = {
      stateRotation = stateRotation.next
    },
    modifier = modifier
      .graphicsLayer {
        if (axis == RotationAxis.AxisX) {
          rotationX = rotation.value
        } else {
          rotationY = rotation.value
        }
        cameraDistance = 12f * density
      },
  ) {
    if (rotation.value <= 90f) {
      Box(
        Modifier.fillMaxSize()
      ) {
        front()
      }
    } else {
      Box(
        Modifier
          .fillMaxSize()
          .graphicsLayer {
            if (axis == RotationAxis.AxisX) {
              rotationX = 180f
            } else {
              rotationY = 180f
            }
          },
      ) {
        Image(
          painterResource(sign.image),
          contentDescription = "",
          contentScale = ContentScale.Fit,
          modifier = Modifier.fillMaxSize()
        )
      }
    }*//*
  }*/

}