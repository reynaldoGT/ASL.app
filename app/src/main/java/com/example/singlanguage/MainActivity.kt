package com.example.singlanguage

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.singlanguage.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    //1 using the binding in activities
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //2 using the binding in activities
        binding = ActivityMainBinding.inflate((layoutInflater))
        setContentView(binding.root)

        showSelectedFragment(SendMessage())


        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
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