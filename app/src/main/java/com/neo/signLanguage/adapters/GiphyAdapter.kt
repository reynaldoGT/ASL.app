package com.neo.signLanguage.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neo.signLanguage.R
import com.neo.signLanguage.models.Datum
import com.neo.signLanguage.models.GhiphyReponse

class GiphyAdapter(private val images: List<Datum>, var context: Context) :
    RecyclerView.Adapter<GiphyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiphyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return GiphyViewHolder(
            layoutInflater.inflate(R.layout.template_giphy, parent, false),
            context
        )
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: GiphyViewHolder, position: Int) {
        val items = images[position]
        holder.bind(items.images.original.url, items.title)
    }


}