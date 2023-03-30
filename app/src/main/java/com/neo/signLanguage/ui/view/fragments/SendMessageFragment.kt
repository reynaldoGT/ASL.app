package com.neo.signLanguage.ui.view.fragments

import android.app.ActionBar
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import com.neo.signLanguage.*
import com.neo.signLanguage.adapters.AdapterLettersSendMessage
import com.neo.signLanguage.adapters.ClickListener
import com.neo.signLanguage.data.database.entities.SignEntity
import com.neo.signLanguage.databinding.FragmentSendMessageBinding
import com.neo.signLanguage.ui.view.activities.MainActivity.Companion.database
import com.neo.signLanguage.ui.viewModel.GameViewModel
import com.neo.signLanguage.ui.viewModel.GiphyViewModel
import com.neo.signLanguage.utils.AdUtils
import com.neo.signLanguage.utils.DataSign.Companion.generateListImageSign
import com.neo.signLanguage.utils.SendMessageDC
import com.neo.signLanguage.utils.SharedPreferences
import com.neo.signLanguage.utils.SharedPreferences.getColorShared
import com.neo.signLanguage.utils.SharedPreferences.getDelay
import com.neo.signLanguage.utils.SharedPreferences.getIsSendMessageSliderActive
import com.neo.signLanguage.utils.SharedPreferences.getSelectedTransition
import com.neo.signLanguage.utils.SharedPreferences.getSharedPreferencesHandColor
import com.neo.signLanguage.utils.Utils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*
import java.util.*


class SendMessageFragment : Fragment() {

  private val RECOGNIZE_SPEECH_ACTIVITY = 1
  private var _binding: FragmentSendMessageBinding? = null
  private val binding get() = _binding!!

  private var job: Job? = null

  private var imageView: ImageView? = null

  private val giphyViewModel: GiphyViewModel by activityViewModels()


  private var layoutManager: RecyclerView.LayoutManager? = null
  private var adapter: AdapterLettersSendMessage? = null

  private val model: GameViewModel by viewModels()


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

    AdUtils.initAds(requireContext())
    AdUtils.initListeners()

    val getColorShared = getColorShared(activity as AppCompatActivity)
    if (getSharedPreferencesHandColor(requireContext()) != 0)
      imageView?.setColorFilter(
        getColorShared
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
          return true
        }
        return false
      }
    })
    binding.swChangeLayout.isChecked =
      SharedPreferences.getIsSendMessageSliderActive(requireContext())
    if (SharedPreferences.getIsSendMessageSliderActive(requireContext())) {
      binding.sendMessageWithImageContainer.visibility = View.VISIBLE
      binding.sendMessageSlideContainer.visibility = View.GONE
    } else {
      binding.sendMessageWithImageContainer.visibility = View.GONE
      binding.sendMessageSlideContainer.visibility = View.VISIBLE
    }
    binding.swChangeLayout.setOnCheckedChangeListener { _, isChecked ->
      SharedPreferences.setIsSendMessageSliderActive(requireContext(), isChecked)
      if (isChecked) {
        binding.sendMessageWithImageContainer.visibility = View.VISIBLE
        binding.sendMessageSlideContainer.visibility = View.GONE
      } else {
        binding.sendMessageWithImageContainer.visibility = View.GONE
        binding.sendMessageSlideContainer.visibility = View.VISIBLE
      }
    }
    binding.floatingActionButtonSpeech.setOnClickListener {
      startSpeech()
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
      if (getSharedPreferencesHandColor(requireContext()) != 0)
        imageView!!.setColorFilter(
          getColorShared
        )
      imageView
    }
    setHandAnimation(requireContext())

    /*Functions of message with images*/

    model.setMessageWithImages(Utils.messageToImages(resources.getString(R.string.hello_from_here)))

    model.gridNumbersMessage.observe(requireActivity()) {
      layoutManager = GridLayoutManager(requireContext(), it)
      binding.gridListSing.layoutManager = layoutManager
    }

    model.sendMessageImages.observe(requireActivity()) {
      adapter =
        AdapterLettersSendMessage(
          requireContext(),
          it,
          object : ClickListener {
            override fun onClick(v: View?, position: Int) {}
          })
      binding.gridListSing.adapter = adapter
    }

    binding.edSendMessageWithImage.editText?.setText(
      Utils.getStringByIdName(
        requireContext(),
        "hello_from_here"
      )
    )
    binding.edSendMessageWithImage.editText?.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
      override fun onTextChanged(string: CharSequence?, p1: Int, p2: Int, p3: Int) {
        model.setMessageWithImages(Utils.messageToImages(string.toString()))
      }

      override fun afterTextChanged(p0: Editable?) {}
    })

    binding.slider.value = 7F
    binding.slider.addOnChangeListener(Slider.OnChangeListener { slider, value, fromUser ->
      model.setGridNumbersMessage(slider.value.toInt())
    })
  }

  private fun setHandAnimation(context: Context) {
    val out: Animation?
    val `in`: Animation?
    when (getSelectedTransition(context)) {
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

  private fun generateSingLanguageMessage(sendMessageDC: SendMessageDC) {

    binding.seeCurrentMessage.visibility = requireView().visibility

    binding.btnSendMessage.isEnabled = false
    binding.edSendMessage.isEnabled = false
    binding.edSendMessage.editText?.setText(sendMessageDC.stringCleaned)

    showMessageWithSing(requireContext(), sendMessageDC)
  }

  private fun showMessageWithSing(context: Context, sendMessageDC: SendMessageDC) {
    val counter = -1
    job = GlobalScope.launch(context = Dispatchers.Main) {
      for ((index, item) in sendMessageDC.data.withIndex()) {
        binding.btnSendMessageCancel.isVisible = true
        binding.btnSendMessage.isVisible = false

        delay(getDelay(context).toLong())

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

        val spannable = SpannableStringBuilder(sendMessageDC.stringCleaned.replaceFirstChar {
          if (it.isLowerCase()) it.titlecase(
            Locale.ROOT
          ) else it.toString()
        })
        spannable.setSpan(
          ForegroundColorSpan(
            ContextCompat.getColor(
              requireActivity().applicationContext,
              getSharedPreferencesHandColor(requireActivity())
            )
          ),
          index, // start
          index + 1, // end
          Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        binding.seeCurrentMessage.text = spannable
        if (counter == sendMessageDC.data.size) {
          binding.imageSwitcher.setImageResource(item.image)
        } else {
          binding.imageSwitcher.setImageResource(item.image)
        }
      }
      resetStatus()
    }
  }

  private fun sendMessage(message: String) {
    val generateListImageSign = generateListImageSign(message)
    giphyViewModel.setCurrentMessage(generateListImageSign.stringCleaned, true)

    val maxCharacters = 75
    if (generateListImageSign.stringCleaned.isNotEmpty()) {
      if (generateListImageSign.stringCleaned.length >= maxCharacters) {
        Snackbar.make(
          requireActivity().findViewById(android.R.id.content),
          String.format(getString(R.string.maximum_number_characters, maxCharacters)),
          Snackbar.LENGTH_LONG,
        ).show()
        return
      }
      generateSingLanguageMessage(generateListImageSign)
      lifecycleScope.launch {
        database.getSignDao()
          .addSing(
            SignEntity(0,
              generateListImageSign.stringCleaned.replaceFirstChar {
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
    AdUtils.checkCounter(requireActivity() as AppCompatActivity)
  }


  override fun onDestroy() {
    job?.cancel()
    /*resetStatus()*/
    super.onDestroy()
  }

  override fun onResume() {
    super.onResume()
    if (getSharedPreferencesHandColor(requireContext()) != 0)
      imageView?.setColorFilter(
        getColorShared(requireContext())
      )
  }

  @Deprecated("Deprecated in Java")
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    when (requestCode) {
      RECOGNIZE_SPEECH_ACTIVITY -> {
        if (resultCode == Activity.RESULT_OK && null != data) {
          val info = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
          val text = info?.get(0)

          if (getIsSendMessageSliderActive(requireContext())) {
            binding.edSendMessage.editText?.setText(text)
            cancelMessage()
            sendMessage(text!!)
          } else {
            binding.edSendMessageWithImage.editText?.setText(text)
          }

        }
      }
    }
    super.onActivityResult(requestCode, resultCode, data)
  }

  private fun startSpeech() {
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
