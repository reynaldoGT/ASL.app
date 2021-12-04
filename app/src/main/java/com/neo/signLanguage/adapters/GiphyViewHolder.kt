package com.neo.signLanguage.adapters

import android.content.Context


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neo.signLanguage.ClickListener
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.TemplateGiphyBinding
import com.orhanobut.logger.Logger


class GiphyViewHolder(
    var context: Context,
    view: View,
    listener: ClickListener
) : RecyclerView.ViewHolder(view),
    View.OnClickListener {
    var listener: ClickListener? = null

    init {
        this.listener = listener
        view.setOnClickListener(this)
    }

    private val binding = TemplateGiphyBinding.bind(view)
    fun bind(image: String, text: String) {

        Glide.with(context)
            .asGif()
            .load(image)
            .placeholder(R.drawable.ic_0_number)
            .error(R.drawable.ic_x_letter)
            .into(binding.ivDog)

    }

    override fun onClick(v: View?) {
        this.listener?.onClick(v!!, adapterPosition)
    }
}