package com.neo.signLanguage.ui.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import com.neo.signLanguage.adapters.ClickListener
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.ColorAdapter
import com.neo.signLanguage.databinding.SettingsActivityBinding
import com.neo.signLanguage.ui.view.activities.TabNavigatorActivity.Companion.sharedPrefs
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import com.neo.signLanguage.utils.DataSign

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: SettingsActivityBinding
    private var adaptador: ColorAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.detailToolbar.setTitle(R.string.settings_title)
        this.setSupportActionBar(binding.detailToolbar)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        binding.switchChangeTheme.isChecked = sharedPrefs.getTheme()
        binding.switchChangeVibration.isChecked = sharedPrefs.getVibration()
        binding.cbTransition.isChecked = sharedPrefs.getShowTransition()
        binding.currentDelayValue.text = sharedPrefs.getDelay().toString() + "ms."

        binding.currentColor.backgroundTintList =
            ContextCompat.getColorStateList(this, sharedPrefs.getColor())
        binding.switchChangeTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPrefs.setTheme(isChecked)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPrefs.setTheme(isChecked)
            }
        }
        binding.switchChangeVibration.setOnCheckedChangeListener { _, isChecked ->
            sharedPrefs.setVibration(isChecked)
        }

        binding.slider.value = sharedPrefs.getDelay().toFloat()
        binding.slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                binding.currentDelayValue.text = "${slider.value.toInt()} ms"
                sharedPrefs.setDelay(slider.value.toInt())
            }
        })
        layoutManager = GridLayoutManager(this, 7)
        binding.rvColors.layoutManager = layoutManager

        val colorList = DataSign.getColorsList(this)
        adaptador =
            ColorAdapter(this, colorList, object : ClickListener {
                override fun onClick(v: View?, position: Int) {
                    sharedPrefs.setColor(colorList[position].colorValue)
                    showSnackBar(colorList[position].colorName)
                    binding.currentColor?.backgroundTintList =
                        resources?.getColorStateList(colorList[position]?.colorValue!!)

                }
            })
        binding.rvColors.adapter = adaptador
        showOptionsTransition()
        if (sharedPrefs.getShowTransition()) {
            setupRadioGroup()
        }
    }

    private fun showSnackBar(colorName: String) {
        val snackBar = Snackbar.make(
            findViewById(android.R.id.content),
            "${getString(R.string.new_color_changed)} $colorName",
            Snackbar.LENGTH_LONG,
        ).setAction("OK") {}
        snackBar.show()
    }

    private fun showOptionsTransition() {

        if (sharedPrefs.getShowTransition()) {
            binding.radioGroup.visibility = View.VISIBLE
            if (sharedPrefs.getSelectedTransition() != -1) {
                (binding.radioGroup.getChildAt(sharedPrefs.getSelectedTransition()) as RadioButton).isChecked =
                    true
            } else {
                return
            }

        } else {
            binding.radioGroup.visibility = View.GONE
        }

        binding.cbTransition.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.radioGroup.visibility = View.VISIBLE
                sharedPrefs.setShowTransition(true)
                (binding.radioGroup.getChildAt(0) as RadioButton).isChecked = true
                sharedPrefs.setSelectedTransition(0)


            } else {
                binding.radioGroup.visibility = View.GONE
                sharedPrefs.setShowTransition(false)
                /*binding.radioGroup.clearCheck()*/
                sharedPrefs.setSelectedTransition(-1)

            }
        }
    }

    private fun setupRadioGroup() {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radioButton: View = binding.radioGroup.findViewById(checkedId)
            val index: Int = binding.radioGroup.indexOfChild(radioButton)
            sharedPrefs.setSelectedTransition(index)

        }

    }


}