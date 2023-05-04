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
import com.neo.signLanguage.utils.SharedPreferences.getDelay
import com.neo.signLanguage.utils.SharedPreferences.getPlaySoundInGames
import com.neo.signLanguage.utils.SharedPreferences.getSelectedTransition
import com.neo.signLanguage.utils.SharedPreferences.getSharedPreferencesHandColor
import com.neo.signLanguage.utils.SharedPreferences.getShowTransition
import com.neo.signLanguage.utils.SharedPreferences.getVibration
import com.neo.signLanguage.utils.SharedPreferences.isDarkTheme
import com.neo.signLanguage.utils.SharedPreferences.setDelay
import com.neo.signLanguage.utils.SharedPreferences.setHandColor
import com.neo.signLanguage.utils.SharedPreferences.setPlaySoundInGames
import com.neo.signLanguage.utils.SharedPreferences.setSelectedTransition
import com.neo.signLanguage.utils.SharedPreferences.setShowTransition
import com.neo.signLanguage.utils.SharedPreferences.setVibration
import com.neo.signLanguage.utils.SharedPreferences.setTheme
import com.neo.signLanguage.utils.Utils.Companion.getHandColor

import com.neo.signLanguage.utils.Utils.Companion.getStringByIdName

class SettingsActivity : AppCompatActivity() {

  private lateinit var binding: SettingsActivityBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = SettingsActivityBinding.inflate(layoutInflater)
    setContentView(binding.root)

    this.setSupportActionBar(binding.settingsToolbar)
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
    val isDarkTheme = remember { mutableStateOf(isDarkTheme(context)) }
    val isVibration = remember { mutableStateOf(getVibration(context)) }
    val isPlaySoundInGames = remember { mutableStateOf(getPlaySoundInGames(context)) }
    var sliderPosition by remember { mutableStateOf(getDelay(context).toFloat()) }
    var currentSelectedColor by remember { mutableStateOf(getSharedPreferencesHandColor(context)) }
    val colorList = DataSign.getColorsList(this)
    val options = listOf(
      getStringByIdName(context, "fade"),
      getStringByIdName(context, "left_to_right"),
      getStringByIdName(context, "right_to_left"),
    )
    val selectedOption = remember { mutableStateOf(getSelectedTransition(context)) }
    val isThereTransition = remember { mutableStateOf(getShowTransition(context)) }
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
              setTheme(this@SettingsActivity, it)
              if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                setHandColor(this@SettingsActivity, R.color.gray300)
              } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                setHandColor(this@SettingsActivity, R.color.primaryColor)
              }
              delegate.applyDayNight()
            })
          CustomSwitchWithTitle(
            label = getStringByIdName(context, "vibration"),
            titleLabel = getStringByIdName(context, "vibration"),
            switchState = isVibration,
            onSwitchChanged = {
              setVibration(this@SettingsActivity, it)
            }
          )
        }
        CustomSwitchWithTitle(
          titleLabel = getStringByIdName(context, "sound"),
          label = getStringByIdName(context, "sound_in_games"),
          switchState = isPlaySoundInGames,
          onSwitchChanged = {
            setPlaySoundInGames(this@SettingsActivity, it)
          }
        )

        labelToShow(getStringByIdName(context, "delay_time"))
        Slider(
          value = sliderPosition,
          steps = 5,
          onValueChange = {
            sliderPosition = it
          },
          valueRange = 250f..2500f,
          onValueChangeFinished = {
            setDelay(this@SettingsActivity, sliderPosition.toInt())
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
          ), style = MaterialTheme.typography.h6,
          modifier = Modifier.padding(vertical = 8.dp),
          color = if (isDarkTheme(this@SettingsActivity)) Color.White else Color.Black
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
                setHandColor(this@SettingsActivity, colorList[index].colorValue)
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
            setShowTransition(this@SettingsActivity, it)
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
                    setSelectedTransition(this@SettingsActivity, index)
                  }
                )
              ) {
                RadioButton(
                  selected = index == selectedOption.value, onClick = {
                    selectedOption.value = index
                    setSelectedTransition(this@SettingsActivity, index)
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
    Text(
      text = label,
      style = MaterialTheme.typography.subtitle2,
      color = if (isDarkTheme(this@SettingsActivity)) Color.White else Color.DarkGray,
    )
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