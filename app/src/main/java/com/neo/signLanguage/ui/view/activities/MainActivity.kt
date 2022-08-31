package com.neo.signLanguage.ui.view.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.room.Room
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.TabAdapter
import com.neo.signLanguage.data.database.entities.SingDatabase
import com.neo.signLanguage.databinding.FragmentTabnavigatorBinding
import com.neo.signLanguage.ui.view.fragments.*
import com.neo.signLanguage.ui.viewModel.GiphyViewModel
import com.neo.signLanguage.utils.NetworkState
import com.neo.signLanguage.utils.SharedPreferences
import java.util.*


class MainActivity : AppCompatActivity() {
  var fragmentAdapter: TabAdapter? = null

  companion object {

    lateinit var binding: FragmentTabnavigatorBinding
    lateinit var sharedPrefs: SharedPreferences
    fun getColorShared(context: Context): Int {
      return ContextCompat.getColor(
        context,
        sharedPrefs.getColor()
      )
    }

    fun getLanguagePhone(): Boolean {
      val language = Locale.getDefault().displayLanguage.toString().lowercase(Locale.ROOT)
      return language == "english"
    }

    lateinit var networkState: NetworkState;
    lateinit var database: SingDatabase
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    val model: GiphyViewModel by viewModels()
    installSplashScreen()
    super.onCreate(savedInstanceState)
    database =
      Room.databaseBuilder(this, SingDatabase::class.java, "sign_db").allowMainThreadQueries()
        .build()

    networkState = NetworkState(this)
    sharedPrefs = SharedPreferences(this)
    binding = FragmentTabnavigatorBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val fm: FragmentManager = supportFragmentManager
    fragmentAdapter = TabAdapter(fm, lifecycle)
    /*binding.viewPager2.adapter = fragmentAdapter*/
    //toolbar
    /*binding.toolbar.setTitle(R.string.app_name)
    setSupportActionBar(binding.toolbar)*/


    /*binding.tabLayout.addTab(
        binding.tabLayout.newTab().setText(getString(R.string.send_message))
    )
    binding.tabLayout.addTab(
        binding.tabLayout.newTab().setText(getString(R.string.movement_signs))
    )*/

    /*binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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
            if (position == 2) {
                model.getAllSingFromDatabase()
            }
            super.onPageSelected(position)
        }
    })*/

    showSelectedFragment(DictionaryFragment())

    binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
      when (menuItem.itemId) {

        R.id.page_2 -> {
          showSelectedFragment(DictionaryFragment())
          true
        }
        /*R.id.page_3 -> {
            showSelectedFragment(ViewNumbersFragment())
            true
        }*/
        R.id.page_4 -> {
          /*showSelectedFragment(FindPairLetters())*/
          showSelectedFragment(GamesMenuFragment())
          true
        }
        else -> false
      }
    }
  }


  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.settings -> {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        true
      }
      R.id.history -> {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_toolbar, menu)
    return super.onCreateOptionsMenu(menu)
  }

  private fun showSelectedFragment(fragment: Fragment) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(R.id.container, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
  }
  /* private fun showSelectedFragment(fragment: Fragment) {
       childFragmentManager.beginTransaction().replace(R.id.container, fragment)
           .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
           .commit()
   }*/

  /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
      activity?.menuInflater?.inflate(R.menu.menu_navigation, menu)
      super.onCreateOptionsMenu(menu, inflater)
  }*/
}