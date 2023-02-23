package com.neo.signLanguage.ui.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.AdapterMenuGameSelect
import com.neo.signLanguage.adapters.ClickListener
import com.neo.signLanguage.data.models.MenuTitle
import com.neo.signLanguage.databinding.FragmentGamesBinding
import com.neo.signLanguage.ui.view.activities.GuessTheWordActivity
import com.neo.signLanguage.ui.view.activities.SendMessageWithImagesActivity
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
    // Using the binding
    _binding = FragmentGamesBinding.inflate(inflater, container, false)
    gameViewModel.getRandomToFindLetter(3)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val titlesMenu = ArrayList<MenuTitle>()
    (activity as AppCompatActivity?)!!.setSupportActionBar(binding.gamesToolbar)
    binding.gamesToolbar.setTitle(R.string.game_and_message)
    /*
    this.setSupportActionBar(binding.detailToolbar)
    val actionbar = supportActionBar
    actionbar?.setDisplayHomeAsUpEnabled(true)*/

    titlesMenu.add(
      MenuTitle(
        getStringByIdName(requireContext(), "test_your_memory"),
        getStringByIdName(requireContext(), "guess_letter_number"),
        R.drawable.ic_brain,
        SelectLevelActivity()
      )
    )
    /*titlesMenu.add(
      MenuTitle(
        getString(R.string.find_the_even_signs),
        getString(R.string.find_correct_letter_or_sign),
        R.drawable.ic_0_number,
        FindPairGameActivity()
      )
    )*/
    titlesMenu.add(
      MenuTitle(
        getStringByIdName(requireContext(), "write_your_message"),
        getStringByIdName(requireContext(),"send_message_with_signs"),
        R.drawable.ic_keyboard,
        SendMessageWithImagesActivity()
      )
    )
    titlesMenu.add(
      MenuTitle(
        getStringByIdName(requireContext(), "write_your_message"),
        getStringByIdName(requireContext(),"send_message_with_signs"),
        R.drawable.ic_10_number,
        GuessFlipCardGameActivity()
      )
    )
    titlesMenu.add(
      MenuTitle(
        getStringByIdName(requireContext(), "write_your_message"),
        getStringByIdName(requireContext(),"send_message_with_signs"),
        R.drawable.ic_10_number,
        GuessTheWordActivity()
      )
    )

    val adapterCustomGrid =
      AdapterMenuGameSelect(requireContext(), titlesMenu, object : ClickListener {
        override fun onClick(v: View?, position: Int) {
          val intent =
            Intent(requireContext(), titlesMenu[position].activity!!::class.java)
          startActivity(intent)
        }
      })
    binding.gridListSing.layoutManager =
      GridLayoutManager(requireContext(), 2)
    binding.gridListSing.adapter = adapterCustomGrid
    initLoad(binding.banner)
  }

}