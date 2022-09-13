package com.neo.signLanguage.ui.view.activities

import android.app.AlertDialog

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.neo.signLanguage.adapters.ClickListener
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.HistoryAdapter
import com.neo.signLanguage.databinding.ActivityHistoryBinding
import com.neo.signLanguage.ui.viewModel.GiphyViewModel
import com.neo.signLanguage.utils.SwipeToDeleteCallback
import com.neo.signLanguage.utils.Utils.Companion.getStringByIdName


class HistoryActivity : AppCompatActivity() {

  private lateinit var binding: ActivityHistoryBinding
  private lateinit var adapter: HistoryAdapter

  private val model: GiphyViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    model.getAllSingFromDatabase()
    binding = ActivityHistoryBinding.inflate(layoutInflater)
    setContentView(binding.root)
    binding.emptyHistoryImageView.setColorFilter(
      getColor(if (MainActivity.sharedPrefs.getTheme()) R.color.white else R.color.black)


    )
    initRecyclerView()

    binding.deleteAllActionButton.setOnClickListener {
      AlertDialog.Builder(this)
        .setTitle(getStringByIdName(this, "delete_question"))
        .setMessage(getStringByIdName(this, "delete_question"))
        .setPositiveButton(
          getStringByIdName(this, "delete"),
        ) { _, _ ->
          model.deleteAllHistory()

        }
        .setNegativeButton(
          R.string.cancel
        ) { _, _ -> Log.d("AlertDialog", "Negative") }
        .setIcon(R.drawable.ic_warning_24)
        .show()
    }

    binding.btnSendMessage.setOnClickListener {
      onBackPressed()
    }
    binding.detailToolbar.title = getStringByIdName(this, "history")
    this.setSupportActionBar(binding.detailToolbar)
    val actionbar = supportActionBar
    actionbar?.setDisplayHomeAsUpEnabled(true)
  }


  private fun initRecyclerView() {

    model.getAllSingFromDatabase
      .observe(this) {
        val arrayList = ArrayList(it)
        adapter =
          HistoryAdapter(
            this,
            arrayList,
            object : ClickListener {
              override fun onClick(v: View?, position: Int) {
                model.setCurrentMessage(it!![position].sing, false)
              }
            })
        binding.rvHistory.layoutManager =
          GridLayoutManager(this, 1)

        if (it.isEmpty()) {
          binding.emptyHistoryLayout.visibility = View.VISIBLE
          binding.deleteAllActionButton.visibility = View.GONE
        } else {
          binding.emptyHistoryLayout.visibility = View.GONE
          binding.deleteAllActionButton.visibility = View.VISIBLE
        }
        val swipeDeleteCallBack = object : SwipeToDeleteCallback() {
          override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            model.deleteById(it!![viewHolder.adapterPosition].id.toLong())
            adapter.removeItem(viewHolder)
          }
        }
        val itemTouchHelper = ItemTouchHelper(swipeDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(binding.rvHistory)

        binding.rvHistory.adapter = adapter
      }
  }
}