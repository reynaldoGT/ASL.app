package com.neo.signLanguage.ui.view.activities

import android.app.AlertDialog

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.neo.signLanguage.ClickListener
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.HistoryAdapter
import com.neo.signLanguage.databinding.ActivityHistoryBinding
import com.neo.signLanguage.ui.viewModel.GiphyViewModel


class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: HistoryAdapter

    private val model: GiphyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.getAllSingFromDatabase()
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        binding.deleteAllActionButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete_question))
                .setMessage(getString(R.string.clear_history_question))
                .setPositiveButton(
                    getString(R.string.delete)
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
        binding.detailToolbar.title = "Historial"
        this.setSupportActionBar(binding.detailToolbar)
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initRecyclerView() {

        model.getAllSingFromDatabase
            .observe(this) {
                adapter =
                    HistoryAdapter(
                        this,
                        it,
                        object : ClickListener {
                            override fun onClick(v: View?, position: Int) {
                                TabNavigatorActivity.binding.viewPager2.currentItem = 0
                                model.setCurrentMessage(it!![position].sing, false)
                            }
                        })
                binding.rvHistory.layoutManager =
                    GridLayoutManager(this, 1)
                binding.rvHistory.adapter = adapter
                if (it.isEmpty()) {
                    binding.emptyHistoryLayout.visibility = View.VISIBLE
                    binding.deleteAllActionButton.visibility = View.GONE
                } else {
                    binding.emptyHistoryLayout.visibility = View.GONE
                    binding.deleteAllActionButton.visibility = View.VISIBLE
                }
            }

    }
}