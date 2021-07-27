package com.neo.singlanguage

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.neo.singlanguage.databinding.FragmentSendMessageBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class SendMessage : Fragment() {

    var lettersArrays: ArrayList<Letter>? = null

    private var _binding: FragmentSendMessageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSendMessageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val shared = Shared()
        lettersArrays = shared.getLetterArray()



        binding.edSendMessage.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->

            binding.seeCurrentMessage.visibility = View.VISIBLE
            binding.seeCurrentMessage.text = binding.edSendMessage.text.toString()


            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                //Perform Code
//                Toast.makeText(this, edSendMessage?.text, Toast.LENGTH_SHORT).show()
                 binding.seeCurrentMessage.text = binding.edSendMessage.text
                /*seeCurrentMessage?.visibility = View.VISIBLE*/
                val createMessage = binding.edSendMessage?.text.toString()

                if (createMessage.isNotEmpty()) {
                    generateSingLanguageMessage(binding.edSendMessage.text.toString())
                }

                return@OnKeyListener true
            }
            false
        })


        binding.ivSing.setOnClickListener {

            changeImage()
        }

        binding.btnSendMessage.setOnClickListener {

            generateSingLanguageMessage(binding.edSendMessage.text.toString())
        }


    }

    private fun changeImage() {

        val randomLetter = (0 until lettersArrays!!.size).random()
        val newLetter: String = lettersArrays!![randomLetter].letter
//        Toast.makeText(this, newLetter, Toast.LENGTH_SHORT).show()

        binding.ivSing.setImageDrawable(
            ContextCompat.getDrawable(
                activity!!.applicationContext, // Context
                lettersArrays!![randomLetter].image
            )
        )
    }

    private fun generateSingLanguageMessage(message: String) {

        binding.seeCurrentMessage.visibility = view!!.visibility
        binding.seeCurrentMessage.text = message

        binding.btnSendMessage.isEnabled = false;
        binding.edSendMessage.isEnabled = false;

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

                delay(timeTimer)
                println("Here after a delay of  $timeTimer milliseconds")

                binding.ivSing.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity!!.applicationContext, // Context
                        index.image
                    )
                )
            }
            binding.btnSendMessage.isEnabled = true
            binding.edSendMessage.isEnabled = true
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
