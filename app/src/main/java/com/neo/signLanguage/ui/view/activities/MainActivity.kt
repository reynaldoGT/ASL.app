package com.neo.signLanguage.ui.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.room.Room
import com.google.android.gms.ads.MobileAds
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.TabAdapter
import com.neo.signLanguage.data.database.entities.SingDatabase
import com.neo.signLanguage.databinding.AcitivityMainBinding
import com.neo.signLanguage.ui.view.fragments.*
import com.neo.signLanguage.utils.SharedPreferences.isDarkTheme


class MainActivity : AppCompatActivity() {
  private var fragmentAdapter: TabAdapter? = null
  private lateinit var binding: AcitivityMainBinding

  companion object {
    lateinit var database: SingDatabase
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    installSplashScreen()
    MobileAds.initialize(this)
    binding = AcitivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    database =
      Room.databaseBuilder(this, SingDatabase::class.java, "sign_db").allowMainThreadQueries()
        .build()

    val fm: FragmentManager = supportFragmentManager
    fragmentAdapter = TabAdapter(fm, lifecycle)

    val games = intent.getBooleanExtra("games", false)
    if (games) {
      showSelectedFragment(GamesMenuFragment())
      binding.bottomNavigation.selectedItemId = R.id.games_page
    } else {
      showSelectedFragment(DictionaryFragment())
    }
    if (isDarkTheme(this)) {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
      delegate.applyDayNight()
    } else {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
      delegate.applyDayNight()
    }

    binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
      when (menuItem.itemId) {
        R.id.dictionary_page -> {
          showSelectedFragment(DictionaryFragment())
          true
        }
        R.id.games_page -> {
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

  private var isFragmentTransactionInProgress = false

  private fun showSelectedFragment(fragment: Fragment) {
    if (!isFragmentTransactionInProgress) {
      isFragmentTransactionInProgress = true
      val transaction = supportFragmentManager.beginTransaction()
      transaction.replace(R.id.container, fragment)
      transaction.addToBackStack(null)
      transaction.commit()
      supportFragmentManager.addOnBackStackChangedListener {
        isFragmentTransactionInProgress = false
      }
    }
  }


  override fun onBackPressed() {
    if (supportFragmentManager.backStackEntryCount == 1) {
      finish() // Cerrar la actividad
    } else {
      super.onBackPressed()
    }
  }
}