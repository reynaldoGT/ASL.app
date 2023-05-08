package com.neo.signLanguage.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neo.signLanguage.R
import com.neo.signLanguage.data.models.GiphyItem
import com.neo.signLanguage.data.models.SingDbModel
import com.neo.signLanguage.data.models.TutorialWelcome
import com.neo.signLanguage.databinding.TemplateHistoryBinding
import com.neo.signLanguage.databinding.TemplateTutorialSlideBinding
import java.lang.ref.WeakReference

class WelcomeSliderAdapter(
  context: Context,
  itemSlideTutorial: ArrayList<TutorialWelcome>,
) : RecyclerView.Adapter<WelcomeSliderAdapter.ViewPagerHolder>() {

  var itemSlideTutorial: ArrayList<TutorialWelcome>? = null
  var viewHolder: ViewPagerHolder? = null
  var context: Context? = null

  init {
    this.context = context
    this.itemSlideTutorial = itemSlideTutorial
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
    val view =
      LayoutInflater.from(parent.context).inflate(R.layout.template_tutorial_slide, parent, false)
    viewHolder = ViewPagerHolder(view)
    return viewHolder!!
  }

  override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
    val item = itemSlideTutorial?.get(position)
    holder.tvSubTitleSliderTutorial?.text = item?.subTitle
    holder.tvTitleSliderTutorial?.text = item?.title
    holder.ivTutorialImage?.setImageResource(item?.image!!)
  }

  override fun getItemCount(): Int {
    return itemSlideTutorial?.count()!!
  }

  class ViewPagerHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = TemplateTutorialSlideBinding.bind(view)
    var tvTitleSliderTutorial: TextView? = null
    var tvSubTitleSliderTutorial: TextView? = null
    var ivTutorialImage: ImageView? = null

    init {
      tvTitleSliderTutorial = binding.tutorialTitle
      tvSubTitleSliderTutorial = binding.tutorialSubtitle
      ivTutorialImage = binding.ivTutorialImage
    }
  }

}