package com.neo.signLanguage.ui.view.fragments


import android.app.ActionBar
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.snackbar.Snackbar
import com.neo.signLanguage.*
import com.neo.signLanguage.data.database.entities.SignEntity
import com.neo.signLanguage.data.models.Sign
import com.neo.signLanguage.databinding.FragmentSendMessageBinding
import com.neo.signLanguage.ui.view.activities.TabNavigatorActivity
import com.neo.signLanguage.ui.view.activities.TabNavigatorActivity.Companion.getColorShared
import com.neo.signLanguage.ui.view.activities.TabNavigatorActivity.Companion.sharedPrefs
import com.neo.signLanguage.ui.viewModel.GiphyViewModel
import com.neo.signLanguage.utils.DataSign.Companion.getLetterArray
import com.neo.signLanguage.utils.Utils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*
import java.util.*


class SendMessageFragment : Fragment() {

    private val RECOGNIZE_SPEECH_ACTIVITY = 1
    private var _binding: FragmentSendMessageBinding? = null
    private val binding get() = _binding!!

    private var interstitial: InterstitialAd? = null

    private var job: Job? = null

    private var imageView: ImageView? = null
    private var stringCleaned: String = ""

    /*private val giphyViewModel by viewModels<GiphyViewModel>()*/
    private val giphyViewModel: GiphyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSendMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        giphyViewModel.currentMessage
            .observe(viewLifecycleOwner) {
                binding.seeCurrentMessage.text =
                    it.ifEmpty { getString(R.string.here_see_your_text) }
                binding.edSendMessage.editText?.setText(it)
            }
        Logger.addLogAdapter(AndroidLogAdapter())

        initAds()
        initListeners()

        if (sharedPrefs.getColor() != 0)
            imageView?.setColorFilter(
                getColorShared(activity as AppCompatActivity)
            )


        binding.edSendMessage.setOnKeyListener(View.OnKeyListener { _, keyCode, _ ->


            binding.seeCurrentMessage.visibility = View.VISIBLE


            if (keyCode == KeyEvent.KEYCODE_ENTER) {

                binding.seeCurrentMessage.text = binding.edSendMessage.editText?.text
                /*seeCurrentMessage?.visibility = View.VISIBLE*/
                val createMessage = binding.edSendMessage.editText?.text.toString()

                sendMessage(createMessage)

                return@OnKeyListener true
            }
            false
        })

        binding.edSendMessage.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // If the event is a key-down event on the "enter" button
                if (event.action === KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    // Perform action on key press
                    /*Toast.makeText(this@HelloFormStuff, edittext.text, Toast.LENGTH_SHORT).show()*/
                    Logger.d("hi from here enter event")

                    return true
                }
                return false
            }
        })

        /*binding.ivSing.setOnClickListener {
            changeImage()
        }*/
        binding.speech.setOnClickListener {
            startSpeech()
            /*throw RuntimeException("Test Crash") // Force a crash*/
        }

        binding.btnSendMessage.setOnClickListener {
            sendMessage(binding.edSendMessage.editText?.text.toString())
        }

        binding.btnSendMessageCancel.setOnClickListener {
            cancelMessage()
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

    private fun generateSingLanguageMessage() {

        binding.seeCurrentMessage.visibility = requireView().visibility

        binding.btnSendMessage.isEnabled = false
        binding.edSendMessage.isEnabled = false

        val arraySentenceSing = ArrayList<Sign>()
        binding.edSendMessage.editText?.setText(stringCleaned)

        val stringArray = stringCleaned.toCharArray()

        for (i in stringArray) {
            for (letterPosition in getLetterArray()) {
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
            for ((index, item) in sentenceInArrayImage.withIndex()) {
                binding.btnSendMessageCancel.isVisible = true
                binding.btnSendMessage.isVisible = false

                delay(sharedPrefs.getDelay().toLong())

                when (item.type) {
                    "space" -> {
                        binding.currentLetter.text = ""
                    }
                    "letter" -> {
                        binding.currentLetter.text =
                            getString(R.string.letter, item.letter.uppercase())
                    }
                    else -> {
                        binding.currentLetter.text =
                            getString(R.string.number, item.letter.uppercase())
                    }
                }


                val spannable = SpannableStringBuilder(stringCleaned.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                })
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
        val re = Regex("[^A-Za-z0-9 ]")
        stringCleaned = message.trim().lowercase()
        stringCleaned = re.replace(stringCleaned, "") // works
        stringCleaned = stringCleaned.replace("\\s+".toRegex(), " ")
        giphyViewModel.setCurrentMessage(stringCleaned, true)
        val maxCharacters: Int = 75
        if (stringCleaned.isNotEmpty()) {
            if (stringCleaned.length >= maxCharacters) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    String.format(getString(R.string.maximum_number_characters, maxCharacters)),
                    Snackbar.LENGTH_LONG,
                ).show()
                return
            }
            generateSingLanguageMessage()

            lifecycleScope.launch {
                TabNavigatorActivity.database.getSignDao()
                    .addSing(
                        SignEntity(0,
                            stringCleaned.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.getDefault()
                                ) else it.toString()
                            })
                    )
            }

        } else {
            Snackbar.make(
                requireActivity().findViewById(android.R.id.content),
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
            requireActivity().applicationContext, // Context,

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            RECOGNIZE_SPEECH_ACTIVITY -> {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    val info = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    val text = info?.get(0)
                    /*textToShow?.text = text*/
                    binding.edSendMessage.editText?.setText(text)
                    cancelMessage()
                    sendMessage(text!!)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun startSpeech() {
        //
        val intentActionRecognize = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intentActionRecognize.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            Utils.getLoLanguageTag()
        )

        try {
            startActivityForResult(intentActionRecognize, RECOGNIZE_SPEECH_ACTIVITY)
        } catch (e: ActivityNotFoundException) {
            Utils.showSnackBar(requireActivity(), R.string.no_microphone)
        }
    }

    private fun cancelMessage() {
        job?.cancel()
        resetStatus()
    }


}
