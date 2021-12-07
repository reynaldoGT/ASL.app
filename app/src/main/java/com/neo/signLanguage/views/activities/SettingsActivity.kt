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
import com.neo.signLanguage.databinding.SettingsActivityBinding
import com.neo.signLanguage.models.Color
import com.neo.signLanguage.views.activities.TabNavigatorActivity.Companion.sharedPrefs
import android.widget.RadioButton
import android.widget.RadioGroup
import com.orhanobut.logger.Logger

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
        binding.cbTransition.isChecked = sharedPrefs.getShowTransition()
        binding.currentDelayValue.text = sharedPrefs.getDelay().toString() + " ms."

        binding.switchChangeTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPrefs.setTheme(isChecked)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPrefs.setTheme(isChecked)
            }
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
                    sharedPrefs.setColor(colorList[position].colorValue)
                    showSnackBar(colorList[position].colorName)
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
            if (binding.radioGroup.visibility == View.VISIBLE) {
                (binding.radioGroup.getChildAt(sharedPrefs.getSelectedTransition()) as RadioButton).isChecked =
                    true
            }
        } else {
            binding.radioGroup.visibility = View.GONE
        }

        binding.cbTransition.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.radioGroup.visibility = View.VISIBLE
                sharedPrefs.setShowTransition(true)
            } else {
                binding.radioGroup.visibility = View.GONE
                sharedPrefs.setShowTransition(false)
                binding.radioGroup.clearCheck()
                sharedPrefs.setSelectedTransition(-1)

            }
        }
    }

    private fun setupRadioGroup() {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radioButton: View = binding.radioGroup.findViewById(checkedId)
            val index: Int = binding.radioGroup.indexOfChild(radioButton)
            Logger.d("index to animate: " + index)
            sharedPrefs.setSelectedTransition(index)
        }

    }


}