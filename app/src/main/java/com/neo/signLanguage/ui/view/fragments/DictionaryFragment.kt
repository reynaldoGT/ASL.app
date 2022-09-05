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
import com.neo.signLanguage.adapters.AdapterLinearLetters
import com.neo.signLanguage.adapters.TabAdapter
import com.neo.signLanguage.databinding.FragmentDictionarySignsBinding
import com.neo.signLanguage.utils.Utils
import com.neo.signLanguage.utils.Utils.Companion.getStringByIdName


class DictionaryFragment : Fragment() {

  private var _binding: FragmentDictionarySignsBinding? = null
  private val binding get() = _binding!!
  var fragmentAdapter: TabAdapter? = null

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

    //toolbar
    binding.toolbar.setTitle(R.string.app_name)
/*change text color*/
    binding.toolbar.setTitleTextColor(resources.getColor(R.color.white))
    (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbar)

    val fm: FragmentManager = parentFragmentManager
    fragmentAdapter = TabAdapter(fm, lifecycle)
    binding.viewPager2.adapter = fragmentAdapter
    binding.tabLayout.addTab(
      binding.tabLayout.newTab().setText(
        getStringByIdName(
          requireContext(),
          "send_message"
        )
      )
      /*binding.tabLayout.newTab().setText(*/

    )
    binding.tabLayout.addTab(
      binding.tabLayout.newTab().setText(
        getStringByIdName(
          requireContext(),
          "letter_and_numbers"
        )
      )
    )
    binding.tabLayout.addTab(
      binding.tabLayout.newTab().setText(
        getStringByIdName(
          requireContext(),
          "search_words"
        )
      )
    )
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