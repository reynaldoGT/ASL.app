package com.neo.signLanguage.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.snackbar.Snackbar
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*
import kotlin.collections.ArrayList
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.neo.signLanguage.*
import com.neo.signLanguage.databinding.FragmentSendMessageBinding
import com.neo.signLanguage.models.Sing
import com.neo.signLanguage.views.activities.GiphyActivity
import com.neo.signLanguage.utils.Shared
import com.neo.signLanguage.views.activities.SettingsActivity
import com.neo.signLanguage.views.activities.TabNavigatorActivity.Companion.getColorShared
import com.neo.signLanguage.views.activities.TabNavigatorActivity.Companion.pref
import java.util.*

class SendMessage : Fragment() {

    private var _binding: FragmentSendMessageBinding? = null
    private val binding get() = _binding!!

    private var interstitial: InterstitialAd? = null

    private var job: Job? = null

    private var lettersArrays: ArrayList<Sing>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSendMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Logger.addLogAdapter(AndroidLogAdapter())

        initAds()
        initListeners()

        val shared = Shared()
        lettersArrays = shared.getLetterArray()

        /*//toolbar
        binding.toolbar.setTitle(R.string.app_name)
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolbar)*/

        if (pref.getColor() != 0)
            binding.ivSing.setColorFilter(
                getColorShared(activity as AppCompatActivity)
            )

        binding.seeCurrentMessage.setOnKeyListener(View.OnKeyListener { _, keyCode, _ ->

            binding.seeCurrentMessage.visibility = View.VISIBLE
            binding.seeCurrentMessage.text = binding.edSendMessage.editText!!.text.toString()

            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                //Perform Code
//                Toast.makeText(this, edSendMessage?.text, Toast.LENGTH_SHORT).show()
                binding.seeCurrentMessage.text = binding.edSendMessage.editText?.text
                /*seeCurrentMessage?.visibility = View.VISIBLE*/
                val createMessage = binding.edSendMessage.editText?.text.toString()

                sendMessage(createMessage)

                return@OnKeyListener true
            }
            false
        })


        binding.ivSing.setOnClickListener {
            changeImage()
        }

        binding.btnSendMessage.setOnClickListener {
            sendMessage(binding.edSendMessage.editText?.text.toString())
        }

        binding.btnSendMessageCancel.setOnClickListener {
            /*break*/
        }

        setHasOptionsMenu(true)
    }

    private fun changeImage() {

        val randomLetter = (0 until lettersArrays!!.size).random()
        val type =
            if (lettersArrays!![randomLetter].type == "letter") getString(R.string.letter) else getString(
                R.string.number
            )
        binding.currentLetter.text =
            "$type ${lettersArrays!![randomLetter].letter.toUpperCase(Locale.ROOT)}"
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

        binding.btnSendMessage.isEnabled = false
        binding.edSendMessage.isEnabled = false

        /*hideKeyboard()*/
        val cleanString = message.trim().toLowerCase(Locale.ROOT)

        val arraySentenceSing = ArrayList<Sing>()

        val stringArray = cleanString.replace(" ", "").toCharArray()

        for (i in stringArray) {
            for (letterPosition in lettersArrays!!) {
                if (letterPosition.letter == i.toString()) {
                    arraySentenceSing.add(letterPosition)
                }
            }
        }

        showMessageWithSing(arraySentenceSing)
    }

    private fun showMessageWithSing(sentenceInArrayImage: ArrayList<Sing>) {

        job = GlobalScope.launch(context = Dispatchers.Main) {
            for (index in sentenceInArrayImage) {
                binding.btnSendMessageCancel.isVisible = true
                binding.btnSendMessageCancel.setOnClickListener {
                    job?.cancel()
                    resetStatus()
                }
                binding.btnSendMessage.isVisible = false

                delay(pref.getDelay().toLong())
                val type =
                    if (index.type == "letter") getString(R.string.letter) else getString(R.string.number)
                binding.currentLetter.text = "$type ${index.letter.toUpperCase(Locale.ROOT)}"
                binding.ivSing.setImageDrawable(
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
            generateSingLanguageMessage(message)
        } else {
            Logger.d("Empty message")
            Snackbar.make(
                activity!!.findViewById(android.R.id.content),
                R.string.empty_message,
                Snackbar.LENGTH_LONG,
            ).show()
        }
    }

    private fun resetStatus() {
        binding.btnSendMessageCancel.isVisible = false
        binding.btnSendMessage.isEnabled = true
        binding.edSendMessage.isEnabled = true
        binding.btnSendMessage.isVisible = true
        checkCounter()

    }

    private fun initListeners() {
        interstitial?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
            }

            override fun onAdShowedFullScreenContent() {
                interstitial = null
            }
        }
    }

    private fun initAds() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            activity!!.applicationContext, // Context,
            getString(R.string.test_intersititial_id),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    interstitial = interstitialAd
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    interstitial = null
                }
            })
    }

    private fun checkCounter() {
        pref.addInOneCounterAd()
        if (pref.getAdCount() == 5) {
            showAds()
            pref.resetAdCount()
            initAds()
        }
    }

    private fun showAds() {
        Logger.d("Show add")
        interstitial?.show(
            activity!!
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                val intent = Intent(activity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.giphy -> {
                val intent = Intent(activity, GiphyActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroy() {
        job?.cancel()
        /*resetStatus()*/
        super.onDestroy()
    }

}
