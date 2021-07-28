package com.example.singlanguage

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class SendMessage : Fragment() {


    var imageview: ImageView? = null
    var button: Button? = null
    var buttonSendMessage: Button? = null
    var edSendMessage: EditText? = null
    var seeCurrentMessage: TextView? = null

    var lettersArrays: ArrayList<Letter>? = null

    var bottomNavigationView: BottomNavigationView? = null;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_send_message, container, false)

        val shared = Shared()
        lettersArrays = shared.getLetterArray()


        imageview = view.findViewById(R.id.imagv)
        buttonSendMessage = view.findViewById(R.id.btnSendMessage)
        edSendMessage = view.findViewById(R.id.editTextEnterMessage)
        seeCurrentMessage = view.findViewById(R.id.seeCurrentMessage)

        bottomNavigationView = activity!!.findViewById(R.id.bottom_navigation)

        edSendMessage?.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->

            seeCurrentMessage?.visibility = View.VISIBLE
            seeCurrentMessage?.text = edSendMessage?.text.toString()


            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                //Perform Code
//                Toast.makeText(this, edSendMessage?.text, Toast.LENGTH_SHORT).show()
                seeCurrentMessage?.text = edSendMessage?.text
                /*seeCurrentMessage?.visibility = View.VISIBLE*/
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

        return view

    }


    private fun changeImage() {

        val randomLetter = (0 until lettersArrays!!.size).random()
        val newLetter: String = lettersArrays!![randomLetter].letter
//        Toast.makeText(this, newLetter, Toast.LENGTH_SHORT).show()

        imageview?.setImageDrawable(
            ContextCompat.getDrawable(
                activity!!.applicationContext, // Context
                lettersArrays!![randomLetter].image
            )
        )
    }

    private fun generateSingLanguageMessage(message: String) {

        seeCurrentMessage?.visibility = view!!.visibility
        seeCurrentMessage?.text = message

        buttonSendMessage?.isEnabled = false;
        edSendMessage?.isEnabled = false;

        /*hideKeyboard()*/

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
                bottomNavigationView?.isVisible = false;
                delay(timeTimer)
                println("Here after a delay of  $timeTimer milliseconds")

                imageview?.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity!!.applicationContext, // Context
                        index.image
                    )
                )
            }
            buttonSendMessage?.isEnabled = true
            edSendMessage?.isEnabled = true
            bottomNavigationView?.isVisible = true;
        }
    }

    /* private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm: InputMethodManager =
                getSystemService(activity!!.applicationContext.IN) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
*/

}
