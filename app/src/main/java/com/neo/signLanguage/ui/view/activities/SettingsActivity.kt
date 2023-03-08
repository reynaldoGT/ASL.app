package com.neo.signLanguage.ui.view.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import com.google.android.material.snackbar.Snackbar
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.SettingsActivityBinding
import com.neo.signLanguage.ui.view.activities.MainActivity.Companion.sharedPrefs
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.neo.signLanguage.ui.view.activities.composables.MyMaterialTheme
import com.neo.signLanguage.ui.view.activities.composables.widgets.CustomSwitchWithTitle
import com.neo.signLanguage.utils.DataSign
import com.neo.signLanguage.utils.Utils.Companion.getStringByIdName

class SettingsActivity : AppCompatActivity() {

  private lateinit var binding: SettingsActivityBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = SettingsActivityBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.detailToolbar.setTitle(R.string.settings_title)
    this.setSupportActionBar(binding.detailToolbar)
    val actionbar = supportActionBar
    actionbar?.setDisplayHomeAsUpEnabled(true)

    binding.composeViewSettingsActivity.setContent {
      MyMaterialTheme(
        content = {
          SettingsContainer()
        }
      )
    }
  }

  fun showSnackBar(colorName: String) {
    val snackBar = Snackbar.make(
      findViewById(android.R.id.content),
      "${getString(R.string.new_color_changed)} $colorName",
      Snackbar.LENGTH_LONG,
    ).setAction("OK") {}
    snackBar.show()
  }

  @Composable
  fun SettingsContainer() {
    val context = LocalContext.current
    val isDarkTheme = remember { mutableStateOf(sharedPrefs.isDarkTheme()) }
    val isVibration = remember { mutableStateOf(sharedPrefs.getVibration()) }
    var sliderPosition by remember { mutableStateOf(sharedPrefs.getDelay().toFloat()) }
    var currentSelectedColor by remember { mutableStateOf(sharedPrefs.getHandColor()) }
    val colorList = DataSign.getColorsList(this)
    val options = listOf("opcion1", "opcion2", "opcion3")
    val selectedOption = remember { mutableStateOf(sharedPrefs.getSelectedTransition()) }
    val isThereTransition = remember { mutableStateOf(sharedPrefs.getShowTransition()) }
    Box(
      modifier = Modifier.padding(8.dp),
      /*
      contentAlignment = Alignment.TopStart*/

    ) {
      Column() {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          CustomSwitchWithTitle(
            titleLabel = getStringByIdName(context, "theme"),
            label = getStringByIdName(context, "dark_theme"),
            switchState = isDarkTheme,
            onSwitchChanged = {
              sharedPrefs.setTheme(it)
              if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPrefs.setHandColor(R.color.gray300)
              } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPrefs.setHandColor(R.color.primaryColor)
              }
            })
          CustomSwitchWithTitle(
            label = getStringByIdName(context, "vibration"),
            titleLabel = getStringByIdName(context, "vibration"),
            switchState = isVibration,
            onSwitchChanged = {
              sharedPrefs.setVibration(it)
            })
        }

        labelToShow(getStringByIdName(context, "delay_time"))
        Slider(

          value = sliderPosition,
          steps = 5,
          onValueChange = {
            sliderPosition = it
          },
          valueRange = 250f..2500f,
          onValueChangeFinished = {
            sharedPrefs.setDelay(sliderPosition.toInt())
          }
        )
        labelToShow(
          getStringByIdName(
            context,
            "current_delay_time"
          ) + " ${sliderPosition.toInt()} ms"
        )

        Text(
          text = getStringByIdName(
            context,
            "change_hand_color"
          ), style = MaterialTheme.typography.h6
        )
        Row() {
          labelToShow(
            getStringByIdName(
              context,
              "current_hand_color"
            )
          )
          Spacer(modifier = Modifier.width(16.dp))
          RoundButton(context, currentSelectedColor, onClick = {})
        }
        LazyVerticalGrid(
          columns = GridCells.Adaptive(25.dp),
          /*columns = GridCells.Fixed(7),*/
          contentPadding = PaddingValues(16.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp),
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          content = {
            items(colorList.size) { index: Int ->
              RoundButton(context, color = colorList[index].colorValue, onClick = {
                currentSelectedColor = colorList[index].colorValue
                showSnackBar(colorList[index].colorName)
                sharedPrefs.setHandColor(colorList[index].colorValue)
              })
            }
          },
        )
        Row(
          verticalAlignment = Alignment.CenterVertically,
        ) {
          labelToShow(
            getStringByIdName(
              context,
              "transition"
            )
          )
          Checkbox(checked = isThereTransition.value, onCheckedChange = {
            isThereTransition.value = it
            sharedPrefs.setShowTransition(it)
          })
        }

        if (isThereTransition.value) {
          Column() {
            options.forEachIndexed { index, text ->
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.selectable(
                  selected = index == selectedOption.value,
                  onClick = {
                    selectedOption.value = index
                    sharedPrefs.setSelectedTransition(index)
                  }
                )
              ) {
                RadioButton(
                  selected = index == selectedOption.value, onClick = {
                    selectedOption.value = index
                    sharedPrefs.setSelectedTransition(index)
                  })
                Text(
                  text = text,
                )
              }
            }
          }
        }

      }
    }
  }


  @Composable
  fun labelToShow(label: String) {
    Text(text = label, style = MaterialTheme.typography.subtitle2)
  }
}


@Composable
fun RoundButton(context: Context, color: Int, onClick: () -> Unit) {
  val colorButton =
    ContextCompat.getColor(
      context,
      color
    )

  Box(
    modifier = Modifier
      .size(25.dp)
      .background(Color(colorButton), CircleShape)
      .clickable(onClick = onClick)
  )
}