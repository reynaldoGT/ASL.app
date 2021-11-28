package com.neo.signLanguage.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import com.neo.signLanguage.ClickListener
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.ColorAdapter
import com.neo.signLanguage.views.activities.MainActivity.Companion.pref
import com.neo.signLanguage.databinding.SettingsActivityBinding
import com.neo.signLanguage.models.Color


class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: SettingsActivityBinding
    private var adaptador: ColorAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.settingsToolbar.setTitle(R.string.settings_title)
        this.setSupportActionBar(binding.settingsToolbar)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        binding.switchChangeTheme.isChecked = pref.getTheme()
        binding.currentDelayValue.text = pref.getDelay().toString() + " ms."

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
                binding.currentDelayValue.text = "${slider.value.toInt()} ms"
                pref.setDelay(slider.value.toInt())
            }
        })
        layoutManager = GridLayoutManager(this, 7)
        binding.rvColors.layoutManager = layoutManager

        val colorList = ArrayList<Color>()
        colorList.add(Color(getString(R.string.blue), R.color.primaryDarkColor))
        colorList.add(Color(getString(R.string.teal), R.color.teal))
        colorList.add(Color(getString(R.string.indigo), R.color.indigo))
        colorList.add(Color(getString(R.string.purple), R.color.purple_200))
        colorList.add(Color(getString(R.string.black), R.color.gray900))
        colorList.add(Color(getString(R.string.gray), R.color.gray300))
        colorList.add(Color(getString(R.string.green), R.color.green_dark))
        colorList.add(Color(getString(R.string.green_light), R.color.lightGreen))
        colorList.add(Color(getString(R.string.deep_orange), R.color.deep_orange))
        colorList.add(Color(getString(R.string.red), R.color.red_dark))
        colorList.add(Color(getString(R.string.pink), R.color.pink))
        colorList.add(Color(getString(R.string.orange), R.color.orange))
        colorList.add(Color(getString(R.string.yellow), R.color.yellow))
        colorList.add(Color(getString(R.string.brawn), R.color.brawn))

        adaptador =
            ColorAdapter(this, colorList, object : ClickListener {
                override fun onClick(v: View?, position: Int) {
                    pref.setColor(colorList[position].colorValue)
                    showSnackBar(colorList[position].colorName)
                }
            })
        binding.rvColors.adapter = adaptador
    }

    private fun showSnackBar(colorName: String) {
        val snackBar = Snackbar.make(
            binding.content,
            "${getString(R.string.new_color_changed)} $colorName",
            Snackbar.LENGTH_LONG,
        ).setAction("OK") {}
        snackBar.show()
    }
}