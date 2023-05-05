package com.neo.signLanguage.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

import com.neo.signLanguage.databinding.FragmentLettersAndNumberSingBinding
import com.neo.signLanguage.ui.view.activities.DetailsSignActivity
import com.neo.signLanguage.ui.view.activities.composables.MyMaterialTheme
import com.neo.signLanguage.ui.view.activities.composables.widgets.CustomSwitch
import com.neo.signLanguage.utils.DataSign.Companion.getLetterAndSignArray
import com.neo.signLanguage.utils.DataSign.Companion.getLetterArray
import com.neo.signLanguage.utils.SharedPreferences.getIsGridLayout
import com.neo.signLanguage.utils.SharedPreferences.getSharedPreferencesHandColor
import com.neo.signLanguage.utils.SharedPreferences.setIsGridLayout
import com.neo.signLanguage.utils.Utils.Companion.getHandColor
import com.neo.signLanguage.utils.Utils.Companion.getStringByIdName


class LetterAndNumbersFragment : Fragment() {

  private var _binding: FragmentLettersAndNumberSingBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentLettersAndNumberSingBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    /*initLoad(binding.banner)*/
    binding.composeViewFragmentLetterAndNumbers.setContent {
      MyMaterialTheme(

        content = {
          ContainerLayout()
        }
      )
    }
  }

  @Composable
  fun ContainerLayout() {

    val switchState = remember { mutableStateOf(getIsGridLayout(requireContext())) }
    Box() {
      Column() {
        Box(modifier = Modifier.align(Alignment.End)) {
          CustomSwitch(
            getStringByIdName(requireContext(), "change_layout"),
            switchState,
            onSwitchChanged = {
              setIsGridLayout(requireContext(), it)
            }
          )
        }
        if (switchState.value) {
          LazyVerticalGridLayout()
        } else {
          LazyHorizontalLayout()
        }
      }
    }
  }

  @Composable
  fun LazyVerticalGridLayout() {
    val lettersToGrid = getLetterAndSignArray()
    LazyVerticalGrid(
      columns = GridCells.Adaptive(128.dp),
      content = {
        items(lettersToGrid.size) { index ->
          Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
              .fillMaxSize()
              .padding(8.dp)
              .clickable {
                goDetails(
                  lettersToGrid[index].image,
                  lettersToGrid[index].letter,
                  lettersToGrid[index].type
                )
              }
          ) {
            Image(
              painter = painterResource(id = lettersToGrid[index].image),
              contentDescription = null,
              colorFilter = getHandColor(requireContext()),
              modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .padding(8.dp)
            )
          }
        }
      }
    )
  }

  @Composable
  fun LazyHorizontalLayout() {
    val lettersArrayToHorizontal = getLetterArray(false)
    LazyColumn(
      modifier = Modifier.wrapContentHeight(),
    ) {
      items(lettersArrayToHorizontal.size) { index ->
        Card(
          modifier = Modifier
            .padding(8.dp)
            .clickable {
              goDetails(
                lettersArrayToHorizontal[index].image,
                lettersArrayToHorizontal[index].letter,
                lettersArrayToHorizontal[index].type
              )
            },
          shape = RoundedCornerShape(16.dp)
        ) {
          Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Image(
              painter = painterResource(id = lettersArrayToHorizontal[index].image),
              contentDescription = null,
              colorFilter = getHandColor(requireContext()),
              modifier = Modifier
                .height(100.dp)
                .aspectRatio(1f)
                .padding(4.dp)
            )
            Text(
              text = lettersArrayToHorizontal[index].letter,
              color = Color(
                ContextCompat.getColor(
                  requireContext(),
                  getSharedPreferencesHandColor(requireContext())
                )
              ),
              modifier = Modifier
                .fillMaxSize(),
              style = MaterialTheme.typography.headlineLarge,
              textAlign = TextAlign.Center,
              fontWeight = FontWeight.W400,
            )
          }
        }
      }
    }
  }

  private fun goDetails(image: Int, letter: String, type: String) {
    val myIntent =
      Intent(activity!!.applicationContext, DetailsSignActivity::class.java)
    myIntent.putExtra("image", image)
    myIntent.putExtra("letter", letter)
    myIntent.putExtra("type", type)
    startActivity(myIntent)
  }
}