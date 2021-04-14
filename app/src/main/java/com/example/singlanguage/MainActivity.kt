package com.example.singlanguage

import android.os.Bundle
import android.view.Menu
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.Theme_SingLanguage)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        showSelectedFragment(SendMessage())


        val botonNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        botonNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.page_1 -> {
                    showSelectedFragment(SendMessage())
                    true
                }
                R.id.page_2 -> {
                    showSelectedFragment(VIewSings())
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