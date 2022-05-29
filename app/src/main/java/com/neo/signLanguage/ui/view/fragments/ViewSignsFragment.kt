package com.neo.signLanguage.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.neo.signLanguage.AdapterGame
import com.neo.signLanguage.ClickListener

import android.content.Intent
import com.neo.signLanguage.AdapterLetters
import com.neo.signLanguage.databinding.FragmentViewSignsBinding
import com.neo.signLanguage.ui.view.activities.DetailsSignActivity
import com.neo.signLanguage.utils.DataSign.Companion.getLetterArray



class ViewSignsFragment : Fragment() {

    private var _binding: FragmentViewSignsBinding? = null
    private val binding get() = _binding!!

    private var adaptador: AdapterLetters? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Using the binding
        _binding = FragmentViewSignsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lettersArray = getLetterArray()

        binding.gridListSing.setHasFixedSize(true)

        layoutManager = GridLayoutManager(requireContext(), 2)
        binding.gridListSing.layoutManager = layoutManager

        adaptador =
            AdapterLetters(
                requireActivity().applicationContext,
                lettersArray,
                object : ClickListener {
                    override fun onClick(v: View?, position: Int) {
                        val myIntent =
                            Intent(activity!!.applicationContext, DetailsSignActivity::class.java)
                        myIntent.putExtra("image", lettersArray[position].image)
                        myIntent.putExtra("letter", lettersArray[position].letter)
                        myIntent.putExtra("type", lettersArray[position].type)
                        startActivity(myIntent)
                    }
                })

        binding.gridListSing.adapter = adaptador

        initLoad()
    }

    private fun initLoad() {
        val adRequest = AdRequest.Builder().build()
        binding.banner.loadAd(adRequest)
    }
}