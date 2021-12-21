package com.neo.signLanguage.ui.view.fragments


import android.app.ActionBar
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
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
import com.neo.signLanguage.*
import com.neo.signLanguage.databinding.FragmentSendMessageBinding
import com.neo.signLanguage.data.models.Sign
import com.neo.signLanguage.utils.DataSign
import com.neo.signLanguage.ui.view.activities.TabNavigatorActivity.Companion.getColorShared
import com.neo.signLanguage.ui.view.activities.TabNavigatorActivity.Companion.sharedPrefs
import java.util.*

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.neo.signLanguage.ui.viewModel.GiphyViewModel
import com.neo.signLanguage.utils.DataSign.Companion.getLetterArray


class SendMessageFragment : Fragment() {

    private var _binding: FragmentSendMessageBinding? = null
    private val binding get() = _binding!!

    private var interstitial: InterstitialAd? = null

    private var job: Job? = null

    private var lettersArrays: ArrayList<Sign>? = null
    private var imageView: ImageView? = null
    private var stringCleaned: String? = null
    private val giphyViewModel by viewModels<GiphyViewModel>()
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

        lettersArrays = getLetterArray()

        if (sharedPrefs.getColor() != 0)
            imageView?.setColorFilter(
                getColorShared(activity as AppCompatActivity)
            )

        giphyViewModel.currentMessage.observe(viewLifecycleOwner, {
            Logger.d(it)
            binding.seeCurrentMessage.text = if (it.isNotEmpty()) it
            else getString(R.string.here_see_your_text)
            binding.edSendMessage.editText?.setText(it)
        })

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
            imageView!!.setImageResource(R.drawable.ic_a_only_sing)
            if (sharedPrefs.getColor() != 0)
                imageView!!.setColorFilter(
                    getColorShared(activity as AppCompatActivity)
                )
            imageView
        }
        setHandAnimation()
    }

    private fun setHandAnimation() {

        val out: Animation?
        val `in`: Animation?
        when (sharedPrefs.getSelectedTransition()) {
            0 -> {
                `in` = AnimationUtils.loadAnimation(activity, android.R.anim.fade_in)
                out = AnimationUtils.loadAnimation(activity, android.R.anim.fade_out)
            }
            2 -> {
                `in` = AnimationUtils.loadAnimation(activity, R.anim.from_right)
                out = AnimationUtils.loadAnimation(activity, R.anim.to_left)
            }
            3 -> {
                `in` = AnimationUtils.loadAnimation(activity, R.anim.from_left)
                out = AnimationUtils.loadAnimation(activity, R.anim.to_right)
            }
            else -> {
                return
            }
        }
        binding.imageSwitcher.outAnimation = out
        binding.imageSwitcher.inAnimation = `in`
    }

    private fun generateSingLanguageMessage(message: String) {
        giphyViewModel.setCurrentMessage(message)
        binding.seeCurrentMessage.visibility = requireView().visibility
        binding.seeCurrentMessage.text = message

        binding.btnSendMessage.isEnabled = false
        binding.edSendMessage.isEnabled = false
/*hideKeyboard()*/
        val cleanString = message.trim().toLowerCase(Locale.ROOT)

        val arraySentenceSing = ArrayList<Sign>()
        stringCleaned = cleanString.replace(" ", "").replace(",", "").replace(".", "")
        val stringArray =
            cleanString.replace(" ", "").replace(",", "").replace(".", "").toCharArray()

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
        val counter = -1
        job = GlobalScope.launch(context = Dispatchers.Main) {
            /*for (index in sentenceInArrayImage) {*/
            for ((index, item) in sentenceInArrayImage.withIndex()) {
                binding.btnSendMessageCancel.isVisible = true

                binding.btnSendMessage.isVisible = false

                delay(sharedPrefs.getDelay().toLong())
                val type =
                    if (item.type == "letter") getString(R.string.letter) else getString(R.string.number)
                binding.currentLetter.text = "$type ${item.letter.toUpperCase(Locale.ROOT)}"


                val spannable = SpannableStringBuilder(stringCleaned?.capitalize(Locale.ROOT))
                spannable.setSpan(
                    ForegroundColorSpan(
                        ContextCompat.getColor(
                            requireActivity().applicationContext,
                            sharedPrefs.getColor()
                        )
                    ),
                    index, // start
                    index + 1, // end
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )
                binding.seeCurrentMessage.text = spannable
                if (counter == sentenceInArrayImage.size) {
                    binding.imageSwitcher.setImageResource(item.image)
                } else {
                    binding.imageSwitcher.setImageResource(item.image)
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
                requireActivity()!!.findViewById(android.R.id.content),
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
            requireActivity()
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
