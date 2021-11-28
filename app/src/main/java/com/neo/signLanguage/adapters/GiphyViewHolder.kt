package com.neo.signLanguage.adapters

import android.content.Context


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.TemplateGiphyBinding
import com.orhanobut.logger.Logger


class GiphyViewHolder(view: View, var context: Context) : RecyclerView.ViewHolder(view) {
    private val binding = TemplateGiphyBinding.bind(view)
    fun bind(image: String, text:String) {
        /*Picasso.get().load(image).into(binding.ivDog)*/
        Logger.d(image)
        Glide.with(context)
            .asGif()
            .load(image)
            .placeholder(R.drawable.ic_0_number)
            .error(R.drawable.ic_x_letter)
            .into(binding.ivDog)
        /*binding.tvCard.text = text*/
    }
}