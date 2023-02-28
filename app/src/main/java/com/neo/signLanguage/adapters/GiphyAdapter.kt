package com.neo.signLanguage.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neo.signLanguage.R
import com.neo.signLanguage.data.models.GiphyItem

class GiphyAdapter(
    var context: Context,
    private val images: List<GiphyItem>,
    var listener: ClickListener
) :
    RecyclerView.Adapter<GiphyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiphyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return GiphyViewHolder(
            context,
            layoutInflater.inflate(R.layout.template_giphy, parent, false),
            listener
        )
    }

    override fun getItemCount(): Int = images.size
    override fun onBindViewHolder(holder: GiphyViewHolder, position: Int) {
        holder.bind(images[position].images.original.url, images[position].title)
    }

}