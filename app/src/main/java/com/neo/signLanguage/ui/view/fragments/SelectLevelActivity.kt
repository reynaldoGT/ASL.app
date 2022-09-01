package com.neo.signLanguage.ui.view.fragments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

        binding.greeting.setContent {
            culumnxd()
        }

        /* }
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
    }*/


    }

    fun goToGame(difficulty: String) {
        val intent = Intent(this, FindTheLetterGameActivity::class.java)
        intent.putExtra("difficulty", difficulty);
        startActivity(intent)
    }

}

@Composable
fun MyText(text: String) {
    Text(text = text)
}

@Composable
fun culumnxd() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MyText(text = "quw fue")
        Button(onClick = {
            //your onclick code here
        }) {
            Text(text = "This is single butotn")
        }
        Button(onClick = {
            //your onclick code here
        }) {
            Text(text = "This is a other")
        }
        Button(onClick = {
            //your onclick code here
        }) {
            Text(text = "this is a third")
        }
    }
}


@Preview
@Composable
fun previewText() {
    culumnxd()
}

