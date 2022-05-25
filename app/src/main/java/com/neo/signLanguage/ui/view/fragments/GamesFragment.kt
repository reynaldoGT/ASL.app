package com.neo.signLanguage.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.neo.signLanguage.adapters.AdapterPairGame
import com.neo.signLanguage.ClickListener

import com.neo.signLanguage.databinding.FragmentGamesBinding
import com.neo.signLanguage.ui.viewModel.GameViewModel

import kotlin.random.Random


class GamesFragment : Fragment() {


    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!

    private var adaptador: AdapterPairGame? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private val gameViewModel: GameViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Using the binding
        _binding = FragmentGamesBinding.inflate(inflater, container, false)
        gameViewModel.setCurrentMessage(3)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameViewModel.setCurrentMessage(3)
        gameViewModel.amount.observe(viewLifecycleOwner) {
            binding.intents.text = it.toString()
        }

        gameViewModel.randomGameLetters.observe(viewLifecycleOwner) {
            binding.gridListSing.layoutManager = layoutManager
            binding.currentAnswer.text = it.correctAnswer
            binding.gridListSing.setHasFixedSize(true)
            adaptador =
                AdapterPairGame(
                    requireActivity().applicationContext,
                    it.data,
                    object : ClickListener {
                        override fun onClick(v: View?, position: Int) {
                            adaptador?.selectItem(position)
                        }
                    })
            layoutManager = GridLayoutManager(requireActivity().applicationContext, 2)
            binding.gridListSing.adapter = adaptador
        }

        binding.changeletters.setOnClickListener {
            gameViewModel.setCurrentMessage(3)
        }
    }

}