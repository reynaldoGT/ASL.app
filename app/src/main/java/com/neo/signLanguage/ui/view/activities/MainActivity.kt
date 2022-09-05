package com.neo.signLanguage.ui.view.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
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

    showSelectedFragment(DictionaryFragment())
    if (sharedPrefs.getTheme()) {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    } else {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

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
      R.id.videoReward -> {
        val intent = Intent(this, VideReward::class.java)
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
}