package com.neo.signLanguage.ui.view.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.TabAdapter
import com.neo.signLanguage.databinding.FragmentDictionarySignsBinding
import com.neo.signLanguage.utils.Utils.Companion.getStringByIdName


class DictionaryFragment : Fragment() {

  private var _binding: FragmentDictionarySignsBinding? = null
  private val binding get() = _binding!!
  var fragmentAdapter: TabAdapter? = null
  private var currentPosition:Int =0
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

    /*change text color*/
    (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbar)

    val fm: FragmentManager = parentFragmentManager
    fragmentAdapter = TabAdapter(fm, lifecycle)
    /*fragmentAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());*/
    binding.viewPager2.adapter = fragmentAdapter
    binding.viewPager2.isSaveEnabled = false
    binding.tabLayout.addTab(
      binding.tabLayout.newTab().setText(
        getStringByIdName(
          requireContext(),
          "send_message"
        )
      )
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
        currentPosition = tab.position
      }
      override fun onTabUnselected(tab: TabLayout.Tab?) {
      }
      override fun onTabReselected(tab: TabLayout.Tab?) {
      }
    })
    binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
      override fun onPageSelected(position: Int) {
        binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))

        super.onPageSelected(position)
      }
    })
  }
  override fun onResume() {
    super.onResume()
    binding.viewPager2.setCurrentItem(currentPosition, false)
  }
}