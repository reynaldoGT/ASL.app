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
import com.neo.signLanguage.adapters.ClickListener
import com.neo.signLanguage.databinding.FragmentFindPairLettersBinding
import com.neo.signLanguage.ui.viewModel.GameViewModel


class FindPairLetters : Fragment() {


    private var _binding: FragmentFindPairLettersBinding? = null
    private val binding get() = _binding!!

    private var adapter: AdapterPairGame? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private val gameViewModel: GameViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Using the binding
        _binding = FragmentFindPairLettersBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val intents = 3
        gameViewModel.getRandomToFindLetter(intents)
        gameViewModel.intents.observe(viewLifecycleOwner) {
            binding.intents.text = it.toString()
        }

        gameViewModel.randomGameLetters.observe(viewLifecycleOwner) {
            binding.gridListSing.layoutManager = layoutManager
            binding.currentAnswer.text = it.correctAnswer.letter
            binding.gridListSing.setHasFixedSize(true)
            adapter =
                AdapterPairGame(
                    requireActivity().applicationContext,
                    it.data,
                    object : ClickListener {
                        override fun onClick(v: View?, position: Int) {

                            adapter?.selectItem(position)
                        }
                    })
            layoutManager = GridLayoutManager(requireActivity().applicationContext, 2)
            binding.gridListSing.adapter = adapter
        }

        binding.changeletters.setOnClickListener {
            gameViewModel.getRandomToFindLetter(intents)
        }


    }

}