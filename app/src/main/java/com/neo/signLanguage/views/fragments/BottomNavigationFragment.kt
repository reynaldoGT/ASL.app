package com.neo.signLanguage.views.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.FragmentBottomNavigationBinding

import com.neo.signLanguage.views.activities.TabNavigatorActivity.Companion.sharedPrefs


class BottomNavigationFragment : Fragment() {
    private var _binding: FragmentBottomNavigationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Using the binding
        _binding = FragmentBottomNavigationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSelectedFragment(SendMessageFragment())

        if (sharedPrefs.getTheme()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }



        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_1 -> {
                    showSelectedFragment(SendMessageFragment())
                    true
                }
                R.id.page_2 -> {
                    showSelectedFragment(ViewSingsFragment())
                    true
                }
                R.id.page_3 -> {
                    showSelectedFragment(ViewNumbersFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun showSelectedFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().replace(R.id.container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.menu_navigation, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


}