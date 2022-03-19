package com.neo.signLanguage.ui.view.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.neo.signLanguage.ClickListener
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.HistoryAdapter
import com.neo.signLanguage.data.database.entities.SignEntity
import com.neo.signLanguage.databinding.FragmentHistoryBinding
import com.neo.signLanguage.ui.view.activities.TabNavigatorActivity
import com.neo.signLanguage.ui.viewModel.GiphyViewModel


class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HistoryAdapter
    private var responseDb: List<SignEntity>? = null


    private val model: GiphyViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        initRecyclerView()
        binding.deleteAll.setOnClickListener {
            AlertDialog.Builder(requireActivity())
                .setTitle(getString(R.string.delete_question))
                .setMessage(getString(R.string.clear_history_question))
                .setPositiveButton(
                    getString(R.string.delete)
                ) { _, _ ->
                    TabNavigatorActivity.database.getSignDao().deleteAllRaw()
                    TabNavigatorActivity.binding.viewPager2.currentItem = 0
                }
                .setNegativeButton(
                    R.string.cancel
                ) { dialog, which -> Log.d("AlertDialog", "Negative") }
                .show()
        }



        return binding.root
    }

    private fun initRecyclerView() {


        model.getAllSingFromDatabase
            .observe(viewLifecycleOwner) {
                adapter =
                    HistoryAdapter(
                        activity?.applicationContext!!,
                        it,
                        object : ClickListener {
                            override fun onClick(v: View?, position: Int) {
                                TabNavigatorActivity.binding.viewPager2.currentItem = 0
                                model.setCurrentMessage(it!![position].sing, false)
                            }
                        })
                binding.rvHistory.layoutManager =
                    GridLayoutManager(activity?.applicationContext!!, 1)
                binding.rvHistory.adapter = adapter
            }


    }
}