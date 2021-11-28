package com.neo.signLanguage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.neo.signLanguage.databinding.TemplateLetterBinding
import com.neo.signLanguage.models.Sing
import com.neo.signLanguage.views.activities.MainActivity


interface ClickListener {
    fun onClick(v: View?, position: Int)
}

class AdapterLetters(
    context: Context,
    items: ArrayList<Sing>, var listener: ClickListener
) : RecyclerView.Adapter<AdapterLetters.ViewHolder>() {

    var items: ArrayList<Sing>? = null
    var viewHolder: ViewHolder? = null
    var context: Context? = null

    init {
        this.items = items
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista =
            LayoutInflater.from(parent.context).inflate(R.layout.template_letter, parent, false)

        viewHolder = ViewHolder(vista, listener, this.context)

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

    class ViewHolder(vista: View, listener: ClickListener, context: Context?) :
        RecyclerView.ViewHolder(vista),
        View.OnClickListener {
        // Using the binding
        private val binding = TemplateLetterBinding.bind(vista)

        var foto: ImageView? = null
        var nombre: TextView? = null
        var listener: ClickListener? = null

        init {
            foto = binding.imageTemplateView
            if (MainActivity.pref.getColor() != 0)
                binding.imageTemplateView.setColorFilter(
                    ContextCompat.getColor(
                        context!!,
                        MainActivity.pref.getColor()
                    )
                )
            nombre = binding.textViewTemplateView
            this.listener = listener
            vista.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)
        }
    }
}