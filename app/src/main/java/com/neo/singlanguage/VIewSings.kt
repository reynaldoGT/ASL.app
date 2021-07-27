package com.neo.singlanguage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neo.singlanguage.databinding.FragmentVIewSingsBinding


class VIewSings : Fragment() {

    private var _binding: FragmentVIewSingsBinding? = null
    private val binding get() = _binding!!

    var adaptador: AdapterLetters? = null
    var layoutManager: RecyclerView.LayoutManager? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Using the binding
        _binding = FragmentVIewSingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val shared = Shared()
        val lettersArray = shared.getOnlyLettersArray()

        binding.gridList.setHasFixedSize(true)

        layoutManager = GridLayoutManager(activity!!.applicationContext, 2)
        binding.gridList.layoutManager = layoutManager

        adaptador = AdapterLetters(lettersArray)

        binding.gridList.adapter = adaptador
    }


}