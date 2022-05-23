package com.neo.signLanguage

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

import com.neo.signLanguage.data.models.Sign
import com.neo.signLanguage.databinding.TemplateGameBinding
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
    var firstElement: Sign? = null
    var secondElement: Sign? = null

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

        val item = items?.get(position)
        holder.singImage?.setImageResource(item?.image!!)
        holder.nombre?.text = item?.letter

        if (itemsSelected.contains(position)) {
            holder.vista.setBackgroundColor(Color.LTGRAY)
            /*if (itemsSelected.size == 2) {
                if (items?.get(itemsSelected[0])?.letter == items?.get(itemsSelected[1])?.letter) {
                    Logger.d("Iguales")

                    itemsSelected.clear()
                    holder.vista.visibility = View.GONE

                } else {
                    Logger.d("No iguales")
                    itemsSelected.clear()
                }
            }*/
        } else {
            holder.vista.setBackgroundColor(Color.WHITE)
        }

    }

    override fun getItemCount(): Int {
        return items?.count()!!
    }

    fun selectItem(index: Int) {
        if (itemsSelected.contains(index)) {
            itemsSelected.remove(index)
            Logger.d("ya hay un elemento igual")

        } else {
            itemsSelected.add(index)
            this.firstElement = items?.get(itemsSelected[0])

            if (itemsSelected.size == 2) {
                this.secondElement = items?.get(itemsSelected[1])
                if (this.firstElement?.letter!! == this.secondElement?.letter!!) {
                    // eliminar los iguales y resetear los comparadores
                    this.items?.remove(this.firstElement)
                    this.items?.remove(this.secondElement)
                    this.firstElement = null
                    this.secondElement = null
                    this.itemsSelected.clear()
                }
            }

        }
        notifyDataSetChanged()
    }

    class ViewHolderGame(vista: View, listener: ClickListener) :
        RecyclerView.ViewHolder(vista),
        View.OnClickListener {
        // Using the binding
        private val binding = TemplateGameBinding.bind(vista)
        var vista = vista
        var singImage: ImageView? = null
        var nombre: TextView? = null
        var listener: ClickListener? = null
        var constraintLayout: ConstraintLayout? = null

        init {
            singImage = binding.imageTemplateView
            constraintLayout = binding.constraintlayoutContainer
            this.listener = listener
            vista.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)

        }
    }
}