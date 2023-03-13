package com.neo.signLanguage.ui.view.activities.games

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.neo.signLanguage.databinding.ActivityCardMatchingWithArrowsBinding
import com.neo.signLanguage.ui.view.activities.composables.MyMaterialTheme


class CardMatchingWithArrowsActivity : AppCompatActivity() {
  private lateinit var binding: ActivityCardMatchingWithArrowsBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityCardMatchingWithArrowsBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.composeViewCardMatchingWithArrows.setContent {
      MyMaterialTheme(
        content = {
          CardMatchingWithArrowsContent()
        }
      )
    }
  }

  @Composable
  fun CardMatchingWithArrowsContent() {

    /*val materialBlue700= Color(0xFF1976D2)*/
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

    Scaffold(
      scaffoldState = scaffoldState,
      topBar = { TopAppBar(title = { Text("TopAppBar") })},
      floatingActionButtonPosition = FabPosition.End,
      floatingActionButton = {
        FloatingActionButton(onClick = {}) {
          Text("X")
        }
      },
      drawerContent = { Text(text = "drawerContent") },
      content = { Text("BodyContent") },
      /*bottomBar = { BottomAppBar(backgroundColor = materialBlue700) { Text("BottomAppBar") } }*/
    )
  }
}