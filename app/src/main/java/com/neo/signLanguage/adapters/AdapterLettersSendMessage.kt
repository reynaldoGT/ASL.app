package com.neo.signLanguage.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.neo.signLanguage.R
import com.neo.signLanguage.data.models.Sign
import com.neo.signLanguage.databinding.TemplateLetterSendMessageBinding
import com.neo.signLanguage.ui.view.activities.MainActivity.Companion.sharedPrefs


class AdapterLettersSendMessage(
    context: Context,
    items: ArrayList<Sign>,
    var listener: ClickListener
) : RecyclerView.Adapter<AdapterLettersSendMessage.ViewHolder>() {

    var items: ArrayList<Sign>? = null
    var viewHolder: ViewHolder? = null
    var context: Context? = null

    init {
        this.items = items
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.template_letter_send_message, parent, false)

        viewHolder = ViewHolder(vista, listener, this.context)

        return viewHolder!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.image?.setImageResource(item?.image!!)
        holder.nombre?.text = item?.letter
    }


    override fun getItemCount(): Int {
        return items?.count()!!
    }

    class ViewHolder(vista: View, listener: ClickListener, context: Context?) :
        RecyclerView.ViewHolder(vista),
        View.OnClickListener {
        // Using the binding
        private val binding = TemplateLetterSendMessageBinding.bind(vista)

        var image: ImageView? = null
        var nombre: TextView? = null
        var listener: ClickListener? = null

        init {
            image = binding.imageTemplateView
            nombre = binding.textViewTemplateView
            if (sharedPrefs.getColor() != 0) {
                binding.imageTemplateView.setColorFilter(
                    ContextCompat.getColor(
                        context!!,
                        sharedPrefs.getColor()
                    )
                )
                binding.textViewTemplateView.setTextColor(
                    ContextCompat.getColor(
                        context,
                        sharedPrefs.getColor()
                    )
                )
            }

            this.listener = listener
            vista.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)
        }
    }


}