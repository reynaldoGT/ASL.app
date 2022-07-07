package com.neo.signLanguage.ui.view.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.slider.Slider.OnChangeListener
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.ClickListener
import com.neo.signLanguage.adapters.AdapterLettersSendMessage
import com.neo.signLanguage.databinding.ActivitySendMessageWithImagesBinding
import com.neo.signLanguage.ui.viewModel.GameViewModel
import com.neo.signLanguage.utils.Utils.Companion.messageToImages


class SendMessageWithImagesActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySendMessageWithImagesBinding
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adaptador: AdapterLettersSendMessage? = null

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
            adaptador =
                AdapterLettersSendMessage(
                    this,
                    it,
                    object : ClickListener {
                        override fun onClick(v: View?, position: Int) {

                        }
                    })

            binding.gridListSing.adapter = adaptador
        }
        binding.edSendMessage.editText?.setText(resources.getString(R.string.hello_from_here))
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
    }

    private fun initActionBar() {
        binding.detailToolbar.title = "Enviar mensaje con imagenes"
        this.setSupportActionBar(binding.detailToolbar)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }
}