package com.neo.signLanguage.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.neo.signLanguage.R
import com.neo.signLanguage.data.models.Sign
import com.neo.signLanguage.databinding.TemplateGameBinding


class AdapterGame(
    context: Context,
    items: ArrayList<Sign>,
    var listener: ClickListener
) : RecyclerView.Adapter<AdapterGame.ViewHolderGame>() {

    var items: ArrayList<Sign>? = null
    var viewHolder: ViewHolderGame? = null
    var context: Context? = null

    init {
        this.items = items
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGame {
        val vista =
            LayoutInflater.from(parent.context).inflate(R.layout.template_game, parent, false)

        viewHolder = ViewHolderGame(vista, listener)

        return viewHolder!!
    }

    override fun onBindViewHolder(holder: ViewHolderGame, position: Int) {

        val item = items?.get(position)
        holder.singImage?.setImageResource(item?.image!!)
        holder.name?.text = item?.letter

    }

    override fun getItemCount(): Int {
        return items?.count()!!
    }

    class ViewHolderGame(vista: View, listener: ClickListener) :
        RecyclerView.ViewHolder(vista),
        View.OnClickListener {
        // Using the binding
        private val binding = TemplateGameBinding.bind(vista)

        var singImage: ImageView? = null
        var name: TextView? = null
        var listener: ClickListener? = null

        init {
            singImage = binding.imageTemplateView
            this.listener = listener
            vista.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)
        }
    }
}