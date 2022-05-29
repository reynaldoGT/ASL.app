package com.neo.signLanguage.ui.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neo.signLanguage.AdapterLetters
import com.neo.signLanguage.ClickListener
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.AdapterLettersSendMessage
import com.neo.signLanguage.data.models.Sign
import com.neo.signLanguage.databinding.ActivityFindLetterGameBinding
import com.neo.signLanguage.databinding.ActivitySendMessageWithImagesBinding
import com.neo.signLanguage.ui.viewModel.GameViewModel
import com.neo.signLanguage.ui.viewModel.GiphyViewModel
import com.neo.signLanguage.utils.DataSign
import com.neo.signLanguage.utils.Utils.Companion.messageToImages
import com.orhanobut.logger.Logger

class SendMessageWithImagesActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySendMessageWithImagesBinding
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adaptador: AdapterLettersSendMessage? = null

    private val model: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendMessageWithImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var lettersArray = ArrayList<Sign>()

        layoutManager = GridLayoutManager(this, 7)
        binding.gridListSing.layoutManager = layoutManager

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

        binding.edSendMessage.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                model.setMessageWithImages(messageToImages(s.toString()))

                /*adaptador!!.notifyDataSetChanged()*/
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        initActionBar()

    }

    private fun initActionBar(){
        binding.detailToolbar.title = "Enviar mensaje con imagenes"
        this.setSupportActionBar(binding.detailToolbar)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }
}