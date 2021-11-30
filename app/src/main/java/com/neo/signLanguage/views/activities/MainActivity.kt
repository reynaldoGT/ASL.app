package com.neo.signLanguage.views.activities

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.neo.signLanguage.views.fragments.SendMessage
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.ActivityMainBinding
import com.neo.signLanguage.databinding.FragmentViewSingsBinding
import com.neo.signLanguage.views.activities.TabNavigatorActivity.Companion.pref
import com.neo.signLanguage.views.fragments.Numbers
import com.neo.signLanguage.views.fragments.ViewSings


class MainActivity : Fragment() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Using the binding
        _binding = ActivityMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSelectedFragment(SendMessage())

        if (pref.getTheme()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NstO)
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_1 -> {
                    showSelectedFragment(SendMessage())
                    true
                }
                R.id.page_2 -> {
                    showSelectedFragment(ViewSings())
                    true
                }
                R.id.page_3 -> {
                    showSelectedFragment(Numbers())
                    true
                }
                else -> false
            }
        }
    }

    private fun showSelectedFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container, fragment)
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ?.commit()
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_navigation, menu)
        return super.onCreateOptionsMenu(menu)
    }*/

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.menu_navigation, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_navigation, menu)
        return super.onCreateOptionsMenu(menu)
    }*/
}