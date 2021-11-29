package com.neo.signLanguage.views.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.TabAdapter
import com.neo.signLanguage.databinding.ActivityTabnavigatorBinding
import com.neo.signLanguage.utils.SharedPreferences

class TabNavigatorActivity : AppCompatActivity() {

    companion object {
        lateinit var pref: SharedPreferences

        fun getColorShared(context: Context): Int {
            return ContextCompat.getColor(
                context,
                pref.getColor()
            )
        }
    }

    private lateinit var binding: ActivityTabnavigatorBinding
    var tabLayout: TabLayout? = null
    var viewPager2: ViewPager2? = null
    var fragmentAdapter: TabAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pref = SharedPreferences(this)
        binding = ActivityTabnavigatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fm: FragmentManager = supportFragmentManager
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
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout?.selectTab(tabLayout!!.getTabAt(position))
                super.onPageSelected(position)
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