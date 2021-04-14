package com.example.singlanguage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class VIewSings : Fragment() {
    var lista: RecyclerView? = null
    var adaptador: AdapterLetters? = null
    var layoutManager: RecyclerView.LayoutManager? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_v_iew_sings, container, false)

        val shared = Shared()
        val lettersArray = shared.getOnlyLettersArray()


        lista = view.findViewById(R.id.gridList)

        lista?.setHasFixedSize(true)

        layoutManager = GridLayoutManager(activity!!.applicationContext, 2)
        lista?.layoutManager = layoutManager

        adaptador = AdapterLetters(lettersArray)

        lista?.adapter = adaptador

        return view
    }


}