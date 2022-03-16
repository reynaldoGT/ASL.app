package com.neo.signLanguage.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.neo.signLanguage.R
import com.neo.signLanguage.data.database.entities.SignEntity
import com.neo.signLanguage.data.models.SingDbModel
import com.neo.signLanguage.data.models.toDomain
import com.neo.signLanguage.databinding.FragmentGiphyBinding
import com.neo.signLanguage.databinding.FragmentHistoryBinding
import com.neo.signLanguage.ui.view.activities.TabNavigatorActivity
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch


class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            val response: List<SignEntity> =
                TabNavigatorActivity.database.getSignDao().getAllSing()
            Logger.d(response)
            getAllSingFromDatabase(response)
        }
        return binding.root
    }

    private fun getAllSingFromDatabase(response: List<SignEntity>): List<SingDbModel> {
        return response.map { it.toDomain() }
    }

}