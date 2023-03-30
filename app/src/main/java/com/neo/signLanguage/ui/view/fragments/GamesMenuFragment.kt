package com.neo.signLanguage.ui.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.neo.signLanguage.R
import com.neo.signLanguage.data.models.MenuTitle
import com.neo.signLanguage.databinding.FragmentGamesBinding

import com.neo.signLanguage.ui.view.activities.composables.CardWithImage
import com.neo.signLanguage.ui.view.activities.composables.MyMaterialTheme
import com.neo.signLanguage.ui.view.activities.games.*
import com.neo.signLanguage.ui.viewModel.GameViewModel
import com.neo.signLanguage.utils.AdUtils.Companion.initLoad
import com.neo.signLanguage.utils.Utils.Companion.getStringByIdName


class GamesMenuFragment : Fragment() {

  private var _binding: FragmentGamesBinding? = null
  private val binding get() = _binding!!

  private val gameViewModel: GameViewModel by viewModels()
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentGamesBinding.inflate(inflater, container, false)
    gameViewModel.getRandomToFindLetter(3)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    (activity as AppCompatActivity?)!!.setSupportActionBar(binding.gamesToolbar)
    binding.gamesToolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    binding.gamesToolbar.setTitle(R.string.games)
    initLoad(binding.banner)
    binding.fragmentGamesComposeView.setContent {
      MyMaterialTheme(
        content = {
          GamesMenuContent()
        }
      )
    }
  }

  @Composable
  fun GamesMenuContent() {
    val titlesMenu = ArrayList<MenuTitle>()
    titlesMenu.add(
      MenuTitle(
        getStringByIdName(requireContext(), "test_your_memory"),
        getStringByIdName(requireContext(), "guess_letter_number"),
        R.drawable.ic_brain,
        SelectLevelActivity(),
        FindTheLetterGameActivity()
      )
    )
    titlesMenu.add(
      MenuTitle(
        getStringByIdName(requireContext(), "guess_the_word"),
        getStringByIdName(requireContext(), "guess_the_word_desc"),
        R.drawable.ic_books,
        GuessTheWordActivity()
      )
    )
    /*titlesMenu.add(
      MenuTitle(
        getStringByIdName(requireContext(), "write_your_message"),
        getStringByIdName(requireContext(), "send_message_with_signs"),
        R.drawable.ic_keyboard,
        SendMessageWithImagesActivity()
      )
    )*/
    titlesMenu.add(
      MenuTitle(
        getStringByIdName(requireContext(), "flip_game_title"),
        getStringByIdName(requireContext(), "flip_game_subtitle"),
        R.drawable.ic_rotate,
        SelectLevelActivity(),
        GuessFlipCardGameActivity()
      )
    )
    titlesMenu.add(
      MenuTitle(
        getStringByIdName(requireContext(), "game_word_in_Sight"),
        getStringByIdName(requireContext(), "game_word_in_sight_subtitle"),
        R.drawable.ic_word_game,
        CardMatchingWithArrowsActivity(),
      )
    )

    Box() {
      LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize(),


        ) {
        items(titlesMenu.size) { index ->
          CardWithImage(
            title = titlesMenu[index].title,
            subtitle = titlesMenu[index].description,
            image = titlesMenu[index].img,
            onClick = {
              val intent = Intent(requireContext(), titlesMenu[index].activity!!::class.java)
              if (titlesMenu[index].afterActivity != null) {
                intent.putExtra("activityClass", titlesMenu[index].afterActivity!!::class.java)
                intent.putExtra("gameName", titlesMenu[index].title)
              }
              startActivity(intent)
            }
          )
        }
      }
    }
  }
}