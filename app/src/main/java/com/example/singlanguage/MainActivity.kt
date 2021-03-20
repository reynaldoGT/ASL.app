package com.example.singlanguage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    var imageview: ImageView? = null
    var button: Button? = null
    var buttonSendMessage: Button? = null
    var edSendMessage: EditText? = null
    var textViewMessage: TextView? = null

    var lettersArrays: ArrayList<Letter>? = null

    override fun onCreate(savedInstanceState: Bundle?) {

//        Thread.sleep(2000)
        setTheme(R.style.Theme_SingLanguage)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val shared = Shared()
        lettersArrays = shared.getLetterArray()


        imageview = findViewById(R.id.imagv)
        button = findViewById(R.id.mybutton)
        buttonSendMessage = findViewById(R.id.btnSendMessage)
        edSendMessage = findViewById(R.id.editTextEnterMessage)
        textViewMessage = findViewById(R.id.textViewMessage)

        edSendMessage?.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                //Perform Code
//                Toast.makeText(this, edSendMessage?.text, Toast.LENGTH_SHORT).show()
                textViewMessage?.text = edSendMessage?.text
                textViewMessage?.visibility = View.VISIBLE
                val createMessage = edSendMessage?.text.toString()

                if (createMessage.isNotEmpty()) {
                    generateSingLanguageMessage(edSendMessage?.text.toString())
                }

                return@OnKeyListener true
            }
            false
        })

        imageview?.setOnClickListener {

            changeImage()
        }

        buttonSendMessage?.setOnClickListener {

            generateSingLanguageMessage(edSendMessage?.text.toString())
        }
        button?.setOnClickListener {

            val intent = Intent(this, GridAllLetters::class.java)
            startActivity(intent)
        }
    }

    private fun changeImage() {

        val randomLetter = (0 until lettersArrays!!.size).random()
        val newLetter: String = lettersArrays!![randomLetter].letter
//        Toast.makeText(this, newLetter, Toast.LENGTH_SHORT).show()

        imageview?.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext, // Context
                lettersArrays!![randomLetter].image
            )
        )
    }

    private fun generateSingLanguageMessage(message: String) {


        buttonSendMessage?.isEnabled = false;
        edSendMessage?.isEnabled = false;

        hideKeyboard()

        val cleanString = message.trim().toLowerCase(Locale.ROOT)

        val arraySentenceSing = ArrayList<Letter>()
//        val stringArray = message.toCharArray()

        val stringArray = cleanString.replace(" ", "").toCharArray()

        for (i in stringArray) {

            for (letterPosition in lettersArrays!!) {
                if (letterPosition.letter == i.toString()) {
                    arraySentenceSing.add(letterPosition)
                }
            }

        }

        showMessageWithSing(arraySentenceSing, timeTimer = 700)
    }

    private fun showMessageWithSing(sentenceInArrayImage: ArrayList<Letter>, timeTimer: Long) {


        GlobalScope.launch(context = Dispatchers.Main) {
            for (index in sentenceInArrayImage) {

                delay(timeTimer)
                println("Here after a delay of  $timeTimer milliseconds")

                imageview?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, // Context
                        index.image
                    )
                )
            }
            buttonSendMessage?.isEnabled = true
            edSendMessage?.isEnabled = true
        }

    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}