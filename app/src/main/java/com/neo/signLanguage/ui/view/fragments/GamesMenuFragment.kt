package com.neo.signLanguage.ui.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager

import com.neo.signLanguage.adapters.ClickListener
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.AdapterMenuGameSelect
import com.neo.signLanguage.data.models.MenuTitle

import com.neo.signLanguage.databinding.FragmentGamesBinding
import com.neo.signLanguage.ui.view.activities.FindPairGameActivity
import com.neo.signLanguage.ui.view.activities.FindTheLetterGameActivity
import com.neo.signLanguage.ui.view.activities.SendMessageWithImagesActivity
import com.neo.signLanguage.ui.viewModel.GameViewModel


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
        titlesMenu.add(
            MenuTitle(
                "Ejercita tu memoria",
                "Adivina la letra o numero correcto ",
                R.drawable.ic_0_number,
                FindTheLetterGameActivity()
            )
        )
        titlesMenu.add(
            MenuTitle(
                "Encuentra los pares",
                "Busca los la letra y el signo correcto",
                R.drawable.ic_0_number,
                FindPairGameActivity()
            )
        )
        titlesMenu.add(
            MenuTitle(
                "Escribir un mensaje",
                "Mostrar mensaje con signos",
                R.drawable.ic_0_number,
                SendMessageWithImagesActivity()
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

        /*grid.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            when (position) {
                0 -> {
                    val intent = Intent(this, Factorial::class.java)
                    startActivity(intent)
                }
                1 -> {
                    val intent = Intent(this, RaizCuadrada::class.java)
                    startActivity(intent)
                }
                2 -> {
                    val intent = Intent(this, Clima::class.java)
                    startActivity(intent)
                }
                3 -> {
                    val intent = Intent(this, Porcentaje::class.java)
                    startActivity(intent)
                }

                4 -> {
                    val intent = Intent(this, Circulo::class.java)
                    startActivity(intent)
                }
                else -> {
                    Snackbar.make(
                        view, // Parent view
                        "Hi from position $position", // Message to show

                        Snackbar.LENGTH_SHORT // How long to display the message.
                    ).setBackgroundTint(Color.parseColor("#FF108714")).show()
                }
            }
        }*/
    }

}