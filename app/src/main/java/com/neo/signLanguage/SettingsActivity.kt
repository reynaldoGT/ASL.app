package com.neo.signLanguage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.slider.Slider
import com.neo.signLanguage.MainActivity.Companion.pref
import com.neo.signLanguage.databinding.SettingsActivityBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: SettingsActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.settingsToolbar.setTitle(R.string.settings_title)
        this.setSupportActionBar(binding.settingsToolbar)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        binding.switchChangeTheme.isChecked = pref.getTheme()
        binding.currentDelayValue.text = pref.getDelay().toString()+ " ms"

        binding.switchChangeTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                pref.setTheme(isChecked)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                pref.setTheme(isChecked)
            }
        }
        binding.slider.value = pref.getDelay().toFloat()
        binding.slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                binding.currentDelayValue.text = slider.value.toInt().toString() +" ms"
                pref.setDelay(slider.value.toInt())
            }
        })
    }
}