package com.neo.signLanguage.views.fragments


import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.neo.signLanguage.R
import com.neo.signLanguage.Shared
import com.neo.signLanguage.Sing
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList


class SendMessage : Fragment() {


    var imageview: ImageView? = null
    var currentLetter: TextView? = null
    var buttonSendMessage: Button? = null
    var buttonStopMessage: Button? = null
    var edSendMessage: TextInputLayout? = null
    var seeCurrentMessage: TextView? = null
    var parentLayout: View? = null

    var lettersArrays: ArrayList<Sing>? = null

    var bottomNavigationView: BottomNavigationView? = null;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        Logger.addLogAdapter(AndroidLogAdapter())

        val view = inflater.inflate(R.layout.fragment_send_message, container, false)

        val shared = Shared()
        lettersArrays = shared.getLetterArray()


        imageview = view.findViewById(R.id.ivSing)
        edSendMessage = view.findViewById(R.id.edSendMessage)
        seeCurrentMessage = view.findViewById(R.id.seeCurrentMessage)
        currentLetter = view.findViewById(R.id.currentLetter)
        parentLayout = view.findViewById(R.id.layoutSendMessage)
        /* Buttons */
        buttonSendMessage = view.findViewById(R.id.btnSendMessage)
        buttonStopMessage = view.findViewById(R.id.btnSendMessageCancel)

        bottomNavigationView = activity!!.findViewById(R.id.bottom_navigation)

        edSendMessage?.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->

            seeCurrentMessage?.visibility = View.VISIBLE
            seeCurrentMessage?.text = edSendMessage?.editText?.text.toString()


            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                //Perform Code
//                Toast.makeText(this, edSendMessage?.text, Toast.LENGTH_SHORT).show()
                seeCurrentMessage?.text = edSendMessage?.editText?.text
                /*seeCurrentMessage?.visibility = View.VISIBLE*/
                val createMessage = edSendMessage?.editText?.text.toString()

                sendMessage(createMessage)

                return@OnKeyListener true
            }
            false
        })


        imageview?.setOnClickListener {

            changeImage()
        }

        buttonSendMessage?.setOnClickListener {

            sendMessage(edSendMessage?.editText?.text.toString())
        }

        buttonStopMessage?.setOnClickListener {
            /*break;*/
        }


        return view

    }


    private fun changeImage() {

        val randomLetter = (0 until lettersArrays!!.size).random()
        currentLetter?.text =
            lettersArrays!![randomLetter].type + (lettersArrays!![randomLetter].letter).toUpperCase(
                Locale.ROOT
            )
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

        val arraySentenceSing = ArrayList<Sing>()
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

    private fun showMessageWithSing(sentenceInArrayImage: ArrayList<Sing>, timeTimer: Long) {
        var job: Job? = null
        job = GlobalScope.launch(context = Dispatchers.Main) {
            for (index in sentenceInArrayImage) {
                buttonStopMessage?.isVisible = true
                buttonStopMessage?.setOnClickListener {
                    job?.cancel()
                    resetStatus()
                }
                bottomNavigationView?.isVisible = false;
                buttonSendMessage?.isVisible = false

                delay(timeTimer)
                println("Here after a delay of  $timeTimer milliseconds")

                currentLetter?.text = index.type + index.letter.toUpperCase(Locale.ROOT)
                imageview?.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity!!.applicationContext, // Context
                        index.image
                    )
                )
            }
            resetStatus()
        }
    }

    private fun sendMessage(message: String) {
        if (message.isNotEmpty()) {
            generateSingLanguageMessage(message.toString())
        } else {

            Logger.d("Mensaje vacio")

            Snackbar.make(
                activity!!.findViewById(android.R.id.content),
                "Mensaje Vacio",
                Snackbar.LENGTH_LONG,

                ).show();

        }
    }

    private fun resetStatus() {
        buttonStopMessage?.isVisible = false
        buttonSendMessage?.isEnabled = true
        edSendMessage?.isEnabled = true
        bottomNavigationView?.isVisible = true
        buttonSendMessage?.isVisible = true

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
