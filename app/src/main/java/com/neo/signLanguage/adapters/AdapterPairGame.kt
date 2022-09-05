package com.neo.signLanguage.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.neo.signLanguage.R

import com.neo.signLanguage.data.models.Sign
import com.neo.signLanguage.databinding.TemplateGamePairBinding
import com.orhanobut.logger.Logger


class AdapterPairGame(
    context: Context,
    items: ArrayList<Sign>,
    var listener: ClickListener
) : RecyclerView.Adapter<AdapterPairGame.ViewHolderGame>() {

    var items: ArrayList<Sign>? = null
    var viewHolder: ViewHolderGame? = null
    var context: Context? = null
    var itemsSelected: ArrayList<Int> = arrayListOf()


    init {
        this.items = items
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGame {
        val vista =
            LayoutInflater.from(parent.context).inflate(R.layout.template_game_pair, parent, false)

        viewHolder = ViewHolderGame(vista, listener)
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: ViewHolderGame, position: Int) {

        if (itemsSelected.contains(position)) {
            holder.vista.setBackgroundColor(Color.LTGRAY)
        } else {
            holder.vista.setBackgroundColor(Color.WHITE)
        }
        val item = items?.get(position)
        /*holder.nombre?.text = item?.letter
        holder.singImage?.setImageResource(item?.image!!)*/

        if (item?.type == "letter") {
            holder.nombre?.text = item.letter
            holder.nombre?.visibility = View.VISIBLE
        } else {
            holder.singImage?.setImageResource(item?.image!!)
            holder.singImage?.visibility = View.VISIBLE
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items?.count()!!
    }

    fun selectItem(index: Int) {
        Logger.d(index)
        if (itemsSelected.contains(index)) {
            itemsSelected.remove(index)
        } else {
            itemsSelected.add(index)
            /*this.firstElement = items?.get(itemsSelected[0])

            if (itemsSelected.size == 2) {
                Logger.d("ya hay mas de 2 elementos")

                this.secondElement = items?.get(itemsSelected[1])
                if (this.firstElement?.letter!! == this.secondElement?.letter!!) {
                    this.items?.remove(this.firstElement)
                    this.items?.remove(this.secondElement)
                    this.firstElement = null
                    this.secondElement = null
                    this.itemsSelected.clear()
                    notifyDataSetChanged()
                }*//*
            }*/

        }
        notifyDataSetChanged()
    }

    class ViewHolderGame(vista: View, listener: ClickListener) :
        RecyclerView.ViewHolder(vista),
        View.OnClickListener {
        // Using the binding
        private val binding = TemplateGamePairBinding.bind(vista)
        var vista = vista
        var singImage: ImageView? = null
        var nombre: TextView? = null
        var listener: ClickListener? = null
        var constraintLayout: ConstraintLayout? = null

        init {
            singImage = binding.imageTemplateView
            constraintLayout = binding.constraintlayoutContainer
            nombre = binding.letter
            this.listener = listener
            vista.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)

        }
    }
}