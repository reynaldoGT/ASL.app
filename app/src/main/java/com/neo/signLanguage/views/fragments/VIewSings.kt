package com.neo.signLanguage.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neo.signLanguage.AdapterLetters
import com.neo.signLanguage.Shared
import com.neo.signLanguage.databinding.FragmentViewSingsBinding


class VIewSings : Fragment() {

    private var _binding: FragmentViewSingsBinding? = null
    private val binding get() = _binding!!

    private var adaptador: AdapterLetters? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    val shared = Shared()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Using the binding
        _binding = FragmentViewSingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val lettersArray = shared.getOnlyLettersArray()

        binding.gridListSing.setHasFixedSize(true)

        layoutManager = GridLayoutManager(activity!!.applicationContext, 2)
        binding.gridListSing.layoutManager = layoutManager

        adaptador = AdapterLetters(lettersArray)

        binding.gridListSing.adapter = adaptador
    }

}