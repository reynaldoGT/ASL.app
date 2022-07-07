package com.neo.signLanguage.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.neo.signLanguage.R
import com.neo.signLanguage.data.models.SingDbModel
import com.neo.signLanguage.databinding.TemplateHistoryBinding
import java.lang.ref.WeakReference


class HistoryAdapter(
    context: Context,
    itemsHistory: ArrayList<SingDbModel>,
    var clickListener: ClickListener
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    var itemsHistory: ArrayList<SingDbModel>? = null
    var viewHolder: ViewHolder? = null


    var context: Context? = null

    init {
        this.itemsHistory = itemsHistory
        this.context = context
    }

    fun removeItem(viewHolder: RecyclerView.ViewHolder) {
        itemsHistory?.removeAt(viewHolder.adapterPosition)
        Snackbar.make(viewHolder.itemView, "Elemento eliminado", Snackbar.LENGTH_LONG).show()
        notifyItemRemoved(viewHolder.adapterPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.template_history, parent, false)
        viewHolder = ViewHolder(view, clickListener)
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemsHistory?.get(position)
        holder.tvHistory?.text = item?.sing
    }

    override fun getItemCount(): Int {
        return itemsHistory?.count()!!
    }


    class ViewHolder(vista: View, listener: ClickListener) :
        RecyclerView.ViewHolder(vista),
        View.OnClickListener {
        private val binding = TemplateHistoryBinding.bind(vista)

        var tvHistory: TextView? = null

        var listenerRef: WeakReference<ClickListener>? = null

        init {
            tvHistory = binding.tvHistory
            listenerRef = WeakReference(listener)
            tvHistory!!.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listenerRef?.get()?.onClick(v, adapterPosition)
        }

    }

}