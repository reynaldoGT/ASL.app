package com.neo.signLanguage.ui.view.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.AdapterLetters
import com.neo.signLanguage.adapters.TabAdapter
import com.neo.signLanguage.databinding.FragmentDictionarySignsBinding


class DictionaryFragment : Fragment() {

    private var _binding: FragmentDictionarySignsBinding? = null
    private val binding get() = _binding!!
    var fragmentAdapter: TabAdapter? = null
    private var adaptador: AdapterLetters? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Using the binding
        _binding = FragmentDictionarySignsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        /*val lettersArray = getLetterArray()

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
    }*/

        //toolbar
        binding.toolbar.setTitle(R.string.app_name)

        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbar)
        val fm: FragmentManager = parentFragmentManager
        fragmentAdapter = TabAdapter(fm, lifecycle)
        binding.viewPager2.adapter = fragmentAdapter
        binding.tabLayout.addTab(
            binding.tabLayout.newTab().setText(getString(R.string.send_message))
        )
        binding.tabLayout.addTab(
            binding.tabLayout.newTab().setText(getString(R.string.letter_and_numbers))
        )
        binding.tabLayout.addTab(
            binding.tabLayout.newTab().setText(getString(R.string.search_words))
        )
        /*binding.tabLayout.setse*//*
        // select tab*/
        /*binding.tabLayout.selectTab(binding.tabLayout.getTabAt(2))*/

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager2.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
                /*if (position == 2) {
                    model.getAllSingFromDatabase()
                }*/
                super.onPageSelected(position)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.menu_navigation, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}