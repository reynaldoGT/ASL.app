package com.neo.signLanguage.ui.view.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.TabAdapter
import com.neo.signLanguage.databinding.FragmentTabnavigatorBinding
import com.neo.signLanguage.utils.NetworkState
import com.neo.signLanguage.utils.SharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TabNavigatorActivity : AppCompatActivity() {

    companion object {
        lateinit var sharedPrefs: SharedPreferences
        fun getColorShared(context: Context): Int {
            return ContextCompat.getColor(
                context,
                sharedPrefs.getColor()
            )
        }

        fun getLanguagePhone(): Boolean {
            val language = Locale.getDefault().displayLanguage.toString().toLowerCase(Locale.ROOT)
            return language == "english"
        }

        lateinit var networkState: NetworkState;
    }

    private lateinit var binding: FragmentTabnavigatorBinding

    var fragmentAdapter: TabAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkState = NetworkState(this)
        sharedPrefs = SharedPreferences(this)
        binding = FragmentTabnavigatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fm: FragmentManager = supportFragmentManager
        fragmentAdapter = TabAdapter(fm, lifecycle)
        binding.viewPager2.adapter = fragmentAdapter

        //toolbar
        binding.toolbar.setTitle(R.string.app_name)
        setSupportActionBar(binding.toolbar)


        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.send_message)))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.movement_signs)))

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
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

}