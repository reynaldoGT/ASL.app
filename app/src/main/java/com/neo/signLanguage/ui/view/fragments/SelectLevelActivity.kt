package com.neo.signLanguage.ui.view.fragments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.ColorAdapter
import com.neo.signLanguage.databinding.ActivitySelectLevelBinding

import com.neo.signLanguage.ui.view.activities.FindTheLetterGameActivity


class SelectLevelActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectLevelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectLevelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLevel1.setOnClickListener {
            goToGame("easy")
        }
        binding.btnLevel2.setOnClickListener {
                goToGame("medium")
        }
        binding.btnLevel3.setOnClickListener {
            goToGame("hard")
        }
    }
    fun goToGame(difficulty: String) {
        val intent = Intent(this, FindTheLetterGameActivity::class.java)
        intent.putExtra("difficulty", difficulty);
        startActivity(intent)
    }
}