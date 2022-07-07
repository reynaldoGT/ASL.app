package com.neo.signLanguage.adapters

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.neo.signLanguage.R
import com.neo.signLanguage.data.models.Sign
import com.neo.signLanguage.databinding.TemplateGameRotatePairBinding
import com.neo.signLanguage.ui.view.activities.TabNavigatorActivity.Companion.sharedPrefs


class AdapterLettersGameRotate(
    context: Context,
    items: ArrayList<Sign>,
    var listener: ClickListener
) : RecyclerView.Adapter<AdapterLettersGameRotate.ViewHolder>() {

    var items: ArrayList<Sign>? = null
    var viewHolder: ViewHolder? = null
    var context: Context? = null
    var itemsSelected: ArrayList<Int> = ArrayList()

    init {
        this.items = items
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.template_game_rotate_pair, parent, false)

        viewHolder = ViewHolder(vista, listener, this.context)

        return viewHolder!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.foto?.setImageResource(item?.image!!)
        holder.nombre?.text = item?.letter
        /*holder.rotate()*/

        /*holder.vista.isClickable = false*/
        if (this.itemsSelected.contains(position)) {
            holder.vista.setBackgroundColor(Color.LTGRAY)
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
        } else {
            itemsSelected.add(index)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(
        vista: View,
        listener: ClickListener,
        context: Context?
    ) :
        RecyclerView.ViewHolder(vista),
        View.OnClickListener {
        //animation

        private var frontAnim: AnimatorSet
        private var backAnim: AnimatorSet
        var isFront = true

        // Using the binding
        private val binding = TemplateGameRotatePairBinding.bind(vista)
        var vista = vista
        var foto: ImageView? = null
        var nombre: TextView? = null
        var listener: ClickListener? = null

        init {
            foto = binding.imageCardBack
            if (sharedPrefs.getColor() != 0)
                binding.imageCardBack.setColorFilter(
                    ContextCompat.getColor(
                        context!!,
                        sharedPrefs.getColor()
                    )
                )
            /*nombre = binding.imageCardFront*/
            this.listener = listener
            vista.setOnClickListener(this)

            val scale = context?.resources?.displayMetrics?.density
            binding.imageCardFront.cameraDistance = 8000 * scale!!
            binding.imageCardBack.cameraDistance = 8000 * scale

            frontAnim = AnimatorInflater.loadAnimator(
                context,
                R.animator.front_animation
            ) as AnimatorSet
            backAnim = AnimatorInflater.loadAnimator(
                context,
                R.animator.back_animation
            ) as AnimatorSet
        }

        fun rotate() {
            isFront = if (isFront) {
                frontAnim.setTarget(binding.imageCardFront)
                backAnim.setTarget(binding.imageCardBack)
                frontAnim.start()
                backAnim.start()
                false
            } else {
                frontAnim.setTarget(binding.imageCardBack)
                backAnim.setTarget(binding.imageCardFront)
                backAnim.start()
                frontAnim.start()
                true
            }
        }

        override fun onClick(v: View?) {

            this.listener?.onClick(v!!, adapterPosition)
            rotate()
        }
    }
}