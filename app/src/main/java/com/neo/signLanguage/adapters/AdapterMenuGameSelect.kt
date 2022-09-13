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
import com.neo.signLanguage.data.models.MenuTitle
import com.neo.signLanguage.databinding.TemplateMenuGameBinding
import com.neo.signLanguage.ui.view.activities.MainActivity.Companion.sharedPrefs
import java.lang.ref.WeakReference


class AdapterMenuGameSelect(
  context: Context,
  itemsMenu: ArrayList<MenuTitle>,
  var clickListener: ClickListener
) : RecyclerView.Adapter<AdapterMenuGameSelect.ViewHolder>() {
  var itemsHistory: ArrayList<MenuTitle>? = null
  var viewHolder: ViewHolder? = null


  var context: Context? = null

  init {
    this.itemsHistory = itemsMenu
    this.context = context
  }


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view =
      LayoutInflater.from(parent.context).inflate(R.layout.template_menu_game, parent, false)
    viewHolder = ViewHolder(view, clickListener, context!!)
    return viewHolder!!
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = itemsHistory?.get(position)
    holder.tvTitle?.text = item?.title
    holder.tvDescription?.text = item?.description
    holder.menuImage?.setImageResource(item?.img!!)
  }

  override fun getItemCount(): Int {
    return itemsHistory?.count()!!
  }


  class ViewHolder(vista: View, listener: ClickListener, context: Context) :
    RecyclerView.ViewHolder(vista),
    View.OnClickListener {
    private val binding = TemplateMenuGameBinding.bind(vista)
    var listener: ClickListener? = null
    var tvTitle: TextView? = null
    var tvDescription: TextView? = null
    var menuImage: ImageView? = null
    var listenerRef: WeakReference<ClickListener>? = null

    init {
      tvTitle = binding.tvTitle
      tvDescription = binding.tvDescription
      listenerRef = WeakReference(listener)
      menuImage = binding.tvImage

      binding.tvImage.setColorFilter(

        ContextCompat.getColor(
          context,
          if (sharedPrefs.getTheme()) R.color.white else R.color.black
        )
      )
      this.listener = listener
      vista.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
      this.listenerRef?.get()?.onClick(v, adapterPosition)
    }
  }

}