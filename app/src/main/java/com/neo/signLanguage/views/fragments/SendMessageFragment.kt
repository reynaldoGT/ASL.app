package com.neo.signLanguage.views.fragments

import android.app.ActionBar
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
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
import com.neo.signLanguage.utils.Shared
import com.neo.signLanguage.views.activities.TabNavigatorActivity.Companion.getColorShared
import com.neo.signLanguage.views.activities.TabNavigatorActivity.Companion.pref
import java.util.*

import android.widget.ImageSwitcher
import android.widget.ViewSwitcher
import android.view.animation.Animation
import android.view.animation.AnimationUtils


class SendMessageFragment : Fragment() {

    private var _binding: FragmentSendMessageBinding? = null
    private val binding get() = _binding!!

    private var interstitial: InterstitialAd? = null

    private var job: Job? = null

    private var lettersArrays: ArrayList<Sing>? = null
    private var imageView: ImageView? = null

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

        if (pref.getColor() != 0)
            imageView?.setColorFilter(
                getColorShared(activity as AppCompatActivity)
            )

        binding.seeCurrentMessage.setOnKeyListener(View.OnKeyListener { _, keyCode, _ ->

            binding.seeCurrentMessage.visibility = View.VISIBLE
            binding.seeCurrentMessage.text = binding.edSendMessage.editText!!.text.toString()

            if (keyCode == KeyEvent.KEYCODE_ENTER) {

                binding.seeCurrentMessage.text = binding.edSendMessage.editText?.text
                /*seeCurrentMessage?.visibility = View.VISIBLE*/
                val createMessage = binding.edSendMessage.editText?.text.toString()

                sendMessage(createMessage)

                return@OnKeyListener true
            }
            false
        })

        /*binding.ivSing.setOnClickListener {
            changeImage()
        }*/

        binding.btnSendMessage.setOnClickListener {
            sendMessage(binding.edSendMessage.editText?.text.toString())
        }

        binding.btnSendMessageCancel.setOnClickListener {
            /*break*/
        }

        setHasOptionsMenu(true)
        binding.imageSwitcher.setFactory {
            imageView = ImageView(activity)
            imageView!!.layoutParams =
                FrameLayout.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.WRAP_CONTENT
                )

            imageView!!.scaleType = ImageView.ScaleType.FIT_CENTER
            imageView!!.setImageResource(R.drawable.ic_a_letter)
            if (pref.getColor() != 0)
                imageView!!.setColorFilter(
                    getColorShared(activity as AppCompatActivity)
                )
            imageView
        }

        /*val out: Animation = AnimationUtils.loadAnimation(activity, android.R.anim.slide_in_left)
        val `in`: Animation = AnimationUtils.loadAnimation(activity, android.R.anim.slide_out_right)*/
        val out: Animation = AnimationUtils.loadAnimation(activity, R.anim.to_left)
        val `in`: Animation = AnimationUtils.loadAnimation(activity, R.anim.from_right)

        binding.imageSwitcher.outAnimation = out
        binding.imageSwitcher.inAnimation = `in`

        /*binding.imageSwitcher.inAnimation = AnimationUtils.loadAnimation(activity, R.anim.to_rigth)
        binding.imageSwitcher.outAnimation =
            AnimationUtils.loadAnimation(activity, R.anim.from_left)*/


    }

    /*private fun changeImage() {

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
    }*/

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
        var counter = -1
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
                /*binding.ivSing.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity!!.applicationContext, // Context
                        index.image
                    )
                )*/
                if (counter == sentenceInArrayImage.size) {
                    binding.imageSwitcher.setImageResource(index.image);
                } else {
                    binding.imageSwitcher.setImageResource(index.image);
                }
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
            getString(R.string.test_interstitial_id),
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

    override fun onDestroy() {
        job?.cancel()
        /*resetStatus()*/
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        if (pref.getColor() != 0)
            imageView?.setColorFilter(
                getColorShared(activity as AppCompatActivity)
            )
    }

}
