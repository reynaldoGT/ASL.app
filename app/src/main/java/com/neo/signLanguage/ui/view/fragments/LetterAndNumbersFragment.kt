package com.neo.signLanguage.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.neo.signLanguage.adapters.ClickListener

import android.content.Intent
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.AdapterLetters
import com.neo.signLanguage.adapters.TabAdapter
import com.neo.signLanguage.databinding.FragmentDictionarySignsBinding
import com.neo.signLanguage.databinding.FragmentLettersAndNumberSingBinding
import com.neo.signLanguage.ui.view.activities.DetailsSignActivity
import com.neo.signLanguage.utils.DataSign.Companion.getLetterArray


class LetterAndNumbersFragment : Fragment() {

    private var _binding: FragmentLettersAndNumberSingBinding? = null
    private val binding get() = _binding!!
    var fragmentAdapter: TabAdapter? = null
    private var adaptador: AdapterLetters? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Using the binding
        _binding = FragmentLettersAndNumberSingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lettersArray = getLetterArray(false)

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