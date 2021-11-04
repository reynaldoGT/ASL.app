package com.neo.signLanguage

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.neo.signLanguage.views.fragments.SendMessage
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.neo.signLanguage.views.fragments.Numbers
import com.neo.signLanguage.views.fragments.VIewSings


class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var pref: SharedPreferences
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showSelectedFragment(SendMessage())
        pref = SharedPreferences(this)

        if (pref.getTheme()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_1 -> {
                    showSelectedFragment(SendMessage())
                    true
                }
                R.id.page_2 -> {
                    showSelectedFragment(VIewSings())
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
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_navigation, menu)
        return super.onCreateOptionsMenu(menu)
    }

}