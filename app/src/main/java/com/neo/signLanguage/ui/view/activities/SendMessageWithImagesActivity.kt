package com.neo.signLanguage.ui.view.activities

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider.OnChangeListener
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.AdapterLettersSendMessage
import com.neo.signLanguage.adapters.ClickListener
import com.neo.signLanguage.databinding.ActivitySendMessageWithImagesBinding
import com.neo.signLanguage.ui.viewModel.GameViewModel
import com.neo.signLanguage.utils.AdUtils.Companion.checkCounter
import com.neo.signLanguage.utils.Utils
import com.neo.signLanguage.utils.Utils.Companion.messageToImages


class SendMessageWithImagesActivity : AppCompatActivity() {
  private lateinit var binding: ActivitySendMessageWithImagesBinding
  private val RECOGNIZE_SPEECH_ACTIVITY = 1
  private var layoutManager: RecyclerView.LayoutManager? = null
  private var adapter: AdapterLettersSendMessage? = null

  private val model: GameViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySendMessageWithImagesBinding.inflate(layoutInflater)
    setContentView(binding.root)

    model.setMessageWithImages(messageToImages(resources.getString(R.string.hello_from_here)))

    model.gridNumbersMessage.observe(this) {
      layoutManager = GridLayoutManager(this, it)
      binding.gridListSing.layoutManager = layoutManager
    }

    model.sendMessageImages.observe(this) {
      adapter =
        AdapterLettersSendMessage(
          this,
          it,
          object : ClickListener {
            override fun onClick(v: View?, position: Int) {
            }
          })
      binding.gridListSing.adapter = adapter
    }

    binding.edSendMessage.editText?.setText(
      Utils.getStringByIdName(
        this,
        "hello_from_here"
      )
    )
    binding.edSendMessage.editText?.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
      }

      override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
        model.setMessageWithImages(messageToImages(s.toString()))
      }

      override fun afterTextChanged(p0: Editable?) {

      }

    })
    initActionBar()

    binding.slider.value = 7F
    binding.slider.addOnChangeListener(OnChangeListener { slider, value, fromUser ->
      model.setGridNumbersMessage(slider.value.toInt())
    })
    binding.iconDownload.setOnClickListener {
      /*Utils.hideKeyboard(this)
      binding.cardViewContainer.setDrawingCacheEnabled(true)
      val b: Bitmap = binding.cardViewContainer.getDrawingCache()

      val nameFile = UUID.randomUUID().toString()
      b.compress(
          CompressFormat.JPEG,
          95,
          FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/$nameFile.jpg")
      )*/
      startSpeech()
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    when (requestCode) {
      RECOGNIZE_SPEECH_ACTIVITY -> {
        if (resultCode == Activity.RESULT_OK && null != data) {
          val info = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
          val text = info?.get(0)
          binding.edSendMessage.editText?.setText(text)
          checkCounter(this)
          model.setMessageWithImages(messageToImages(text!!))
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
      Utils.showSnackBar(this, R.string.no_microphone)
    }
  }

  private fun initActionBar() {
    binding.detailToolbar.title = getString(R.string.send_message_with_images)
    this.setSupportActionBar(binding.detailToolbar)
    val actionbar = supportActionBar
    actionbar?.setDisplayHomeAsUpEnabled(true)
  }

  override fun onSupportNavigateUp(): Boolean {
    finish()
    return true
  }
}