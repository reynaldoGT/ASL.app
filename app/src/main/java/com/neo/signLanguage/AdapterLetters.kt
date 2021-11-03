package com.neo.signLanguage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neo.signLanguage.databinding.TemplateLetterBinding


interface ClickListener {
    fun onClick(v: View?, position: Int)
}

class AdapterLetters(
    items: ArrayList<Sing>, var listener: ClickListener
) : RecyclerView.Adapter<AdapterLetters.ViewHolder>() {

    var items: ArrayList<Sing>? = null
    var viewHolder: ViewHolder? = null

    init {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista =
            LayoutInflater.from(parent.context).inflate(R.layout.template_letter, parent, false)

        viewHolder = ViewHolder(vista, listener)

        return viewHolder!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items?.get(position)
        holder.foto?.setImageResource(item?.image!!)
        holder.nombre?.text = item?.letter

    }

    override fun getItemCount(): Int {
        return items?.count()!!
    }

    class ViewHolder(vista: View, listener: ClickListener) : RecyclerView.ViewHolder(vista),
        View.OnClickListener {
        // Using the binding
        private val binding = TemplateLetterBinding.bind(vista)

        var foto: ImageView? = null
        var nombre: TextView? = null
        var listener: ClickListener? = null

        init {
            foto = binding.imageTemplateView
            nombre = binding.textViewTemplateView
            this.listener = listener
            vista.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)
        }
    }
}