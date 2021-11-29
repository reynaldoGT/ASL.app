package com.neo.signLanguage.provider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.TabAdapter
import com.neo.signLanguage.databinding.ActivityDetailsBinding.inflate
import com.neo.signLanguage.databinding.ActivityTabnavigatorBinding
import com.neo.signLanguage.databinding.SettingsActivityBinding
import com.neo.signLanguage.views.activities.SettingsActivity

class TabNavigatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTabnavigatorBinding
    var tabLayout: TabLayout? = null
    var viewPager2: ViewPager2? = null
    var fragmentAdapter: TabAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabnavigatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var fm: FragmentManager = supportFragmentManager
        fragmentAdapter = TabAdapter(fm, lifecycle)
        binding.viewPager2.adapter = fragmentAdapter


        //toolbar
        binding.toolbar.setTitle(R.string.app_name)
        setSupportActionBar(binding.toolbar)

        tabLayout?.addTab(tabLayout!!.newTab().setText("First"))
        tabLayout?.addTab(tabLayout!!.newTab().setText("Second"))

        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager2?.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.giphy -> {
                val intent = Intent(this, GiphyActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}