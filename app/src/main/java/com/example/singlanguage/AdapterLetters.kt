package com.example.singlanguage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.singlanguage.databinding.TemplateLetterBinding

class AdapterLetters(
    items: ArrayList<Letter>

) : RecyclerView.Adapter<AdapterLetters.ViewHolder>() {

    var items: ArrayList<Letter>? = null
    var viewHolder: ViewHolder? = null

    init {
        this.items = items
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista =
            LayoutInflater.from(parent.context).inflate(R.layout.template_letter, parent, false)

        viewHolder = ViewHolder(vista)

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

    class ViewHolder(vista: View) : RecyclerView.ViewHolder(vista) {

        // Using the binding
        private val binding= TemplateLetterBinding.bind(vista)

        var foto: ImageView? = null
        var nombre: TextView? = null

        init {
            foto = binding.imageTemplateView
            nombre = binding.textViewTemplateView
        }
    }
}