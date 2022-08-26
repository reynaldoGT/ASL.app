package com.neo.signLanguage.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neo.signLanguage.R
import com.neo.signLanguage.databinding.FragmentSendMessageBinding
import com.neo.signLanguage.databinding.FragmentTestBinding
import com.neo.signLanguage.databinding.FragmentTranslateBinding
import com.neo.signLanguage.ui.view.activities.MainActivity


class TestFragment : Fragment() {

    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        /*return inflater.inflate(R.layout.fragment_test, container, false)*/
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}