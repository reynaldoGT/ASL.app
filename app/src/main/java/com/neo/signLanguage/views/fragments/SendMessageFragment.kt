package com.neo.signLanguage.views.fragments


import android.app.ActionBar
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
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
import com.neo.signLanguage.models.Sign
import com.neo.signLanguage.utils.Shared
import com.neo.signLanguage.views.activities.TabNavigatorActivity.Companion.getColorShared
import com.neo.signLanguage.views.activities.TabNavigatorActivity.Companion.sharedPrefs
import java.util.*

import android.view.animation.Animation
import android.view.animation.AnimationUtils


class SendMessageFragment : Fragment() {

    private var _binding: FragmentSendMessageBinding? = null
    private val binding get() = _binding!!

    private var interstitial: InterstitialAd? = null

    private var job: Job? = null

    private var lettersArrays: ArrayList<Sign>? = null
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

        if (sharedPrefs.getColor() != 0)
            imageView?.setColorFilter(
                getColorShared(activity as AppCompatActivity)
            )
        binding.seeCurrentMessage.text = if (sharedPrefs.getCurrentMessage()
                .isNotEmpty()
        ) sharedPrefs.getCurrentMessage() else getString(R.string.here_see_your_text)
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
        binding.edSendMessage.editText?.setText(sharedPrefs.getCurrentMessage())
        binding.btnSendMessage.setOnClickListener {
            sendMessage(binding.edSendMessage.editText?.text.toString())
        }

        binding.btnSendMessageCancel.setOnClickListener {
            job?.cancel()
            resetStatus()
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
            if (sharedPrefs.getColor() != 0)
                imageView!!.setColorFilter(
                    getColorShared(activity as AppCompatActivity)
                )
            imageView
        }

        setHandAnimation()

    }

    private fun setHandAnimation() {

        var out: Animation?
        var `in`: Animation?
        when (sharedPrefs.getSelectedTransition()) {
            0 -> {
                out = AnimationUtils.loadAnimation(activity, android.R.anim.fade_out)
                `in` = AnimationUtils.loadAnimation(activity, android.R.anim.fade_out)
            }
            2 -> {
                out = AnimationUtils.loadAnimation(activity, R.anim.to_left)
                `in` = AnimationUtils.loadAnimation(activity, R.anim.from_right)
            }
            3 -> {
                out = AnimationUtils.loadAnimation(activity, R.anim.to_right)
                `in` = AnimationUtils.loadAnimation(activity, R.anim.from_left)
            }
            else -> {
                return
            }
        }
        binding.imageSwitcher.outAnimation = out
        binding.imageSwitcher.inAnimation = `in`
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
        sharedPrefs.setCurrentMessage(message)
        binding.seeCurrentMessage.visibility = view!!.visibility
        binding.seeCurrentMessage.text = message

        binding.btnSendMessage.isEnabled = false
        binding.edSendMessage.isEnabled = false

/*hideKeyboard()*/
        val cleanString = message.trim().toLowerCase(Locale.ROOT)

        val arraySentenceSing = ArrayList<Sign>()

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

    private fun showMessageWithSing(sentenceInArrayImage: ArrayList<Sign>) {
        var counter = -1
        job = GlobalScope.launch(context = Dispatchers.Main) {
            for (index in sentenceInArrayImage) {
                binding.btnSendMessageCancel.isVisible = true

                binding.btnSendMessage.isVisible = false

                delay(sharedPrefs.getDelay().toLong())
                val type =
                    if (index.type == "letter") getString(R.string.letter) else getString(R.string.number)
                binding.currentLetter.text = "$type ${index.letter.toUpperCase(Locale.ROOT)}"
                /*  binding.ivSing.setImageDrawable(
                      ContextCompat.getDrawable(
                          activity!!.applicationContext, // Context
                          index.image
                      )
                  )*/
                if (counter == sentenceInArrayImage.size) {
                    binding.imageSwitcher.setImageResource(index.image)
                } else {
                    binding.imageSwitcher.setImageResource(index.image)
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
        sharedPrefs.addInOneCounterAd()
        if (sharedPrefs.getAdCount() == 5) {
            showAds()
            sharedPrefs.resetAdCount()
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
        if (sharedPrefs.getColor() != 0)
            imageView?.setColorFilter(
                getColorShared(activity as AppCompatActivity)
            )
    }

}
