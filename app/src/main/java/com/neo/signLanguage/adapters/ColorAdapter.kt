package com.neo.signLanguage.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.ColorTemplateBinding
import com.neo.signLanguage.databinding.TemplateLetterBinding
import com.neo.signLanguage.models.Color
import com.neo.signLanguage.ClickListener
import java.lang.ref.WeakReference


class ColorAdapter(
    context: Context,
    itemsColor: ArrayList<Color>,
    var clickListener: ClickListener
) : RecyclerView.Adapter<ColorAdapter.ViewHolder>() {
    var itemsColor: ArrayList<Color>? = null
    var viewHolder: ViewHolder? = null

    var context: Context? = null

    init {
        this.itemsColor = itemsColor
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.color_template, parent, false)
        viewHolder = ViewHolder(view, clickListener, this.context)
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemsColor?.get(position)
        /*holder.buttonColor?.background(item?.colorValue!!)*/
        /*holder.buttonColor.setSupportButtonTintList(ContextCompat.getColorStateList(this, R.color.colorPrimary));*/
        holder.buttonColor?.backgroundTintList =
            context?.getResources()?.getColorStateList(item?.colorValue!!)

    }

    override fun getItemCount(): Int {
        return itemsColor?.count()!!
    }


    class ViewHolder(vista: View, listener: ClickListener, context: Context?) :
        RecyclerView.ViewHolder(vista),
        View.OnClickListener {
        private val binding = ColorTemplateBinding.bind(vista)

        var buttonColor: MaterialButton? = null

        var listenerRef: WeakReference<ClickListener>? = null

        init {
            buttonColor = binding.btnColor

            listenerRef = WeakReference(listener)
            buttonColor!!.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listenerRef?.get()?.onClick(v, adapterPosition)
        }

    }

}