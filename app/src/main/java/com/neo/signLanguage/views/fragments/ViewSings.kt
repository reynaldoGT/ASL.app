package com.neo.signLanguage.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.neo.signLanguage.AdapterLetters
import com.neo.signLanguage.ClickListener
import com.neo.signLanguage.utils.Shared
import com.neo.signLanguage.databinding.FragmentViewSingsBinding
import android.content.Intent
import com.neo.signLanguage.views.DetailsSingActivity


class ViewSings : Fragment() {

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

        adaptador =
            AdapterLetters(activity!!.applicationContext, lettersArray, object : ClickListener {
                override fun onClick(v: View?, position: Int) {
                    val myIntent =
                        Intent(activity!!.applicationContext, DetailsSingActivity::class.java)
                    myIntent.putExtra("image", lettersArray[position].image)
                    myIntent.putExtra("letter", lettersArray[position].letter)
                    myIntent.putExtra("type", lettersArray[position].type)
                    startActivity(myIntent)
                }
            })

        binding.gridListSing.adapter = adaptador

        initLoad()
    }

    private fun initLoad() {
        val adRequest = AdRequest.Builder().build()
        binding.banner.loadAd(adRequest)
    }
}