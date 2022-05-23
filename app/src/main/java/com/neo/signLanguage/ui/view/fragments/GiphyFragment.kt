package com.neo.signLanguage.ui.view.fragments

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.neo.signLanguage.ClickListener
import com.neo.signLanguage.R
import com.neo.signLanguage.adapters.GiphyAdapter
import com.neo.signLanguage.data.models.GiphyItem
import com.neo.signLanguage.databinding.FragmentGiphyBinding
import com.neo.signLanguage.provider.ApiInterfaceTranslate
import com.neo.signLanguage.ui.view.activities.DetailsSignActivity
import com.neo.signLanguage.ui.view.activities.TabNavigatorActivity.Companion.getLanguagePhone
import com.neo.signLanguage.ui.view.activities.TabNavigatorActivity.Companion.networkState
import com.neo.signLanguage.ui.viewModel.GiphyViewModel
import com.neo.signLanguage.ui.viewModel.NetworkViewModel
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.regex.Matcher
import java.util.regex.Pattern


class GiphyFragment : Fragment(), SearchView.OnQueryTextListener {

    private var giphyImages = mutableListOf<GiphyItem>()
    private val giphyViewModel: GiphyViewModel by viewModels()
    private val ViewModelProvider: NetworkViewModel by viewModels()

    private var _binding: FragmentGiphyBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GiphyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Using the binding
        _binding = FragmentGiphyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        ViewModelProvider.connected.observe(viewLifecycleOwner) { connected: Boolean ->

            if (connected) {
                binding.searchBreed.setOnQueryTextListener(this)
                binding.searchBreed.visibility = View.VISIBLE
                initRecyclerView()
                searchGiphy("American Sign Language")
                binding.noInternetLayout.layoutNoConnection.visibility = View.GONE
                binding.rvDogs.visibility = View.VISIBLE
            } else {
                binding.searchBreed.visibility = View.GONE
                binding.rvDogs.visibility = View.GONE
                binding.noInternetLayout.layoutNoConnection.visibility = View.VISIBLE
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.no_connection),
                    Snackbar.LENGTH_LONG,

                    ).setAction("OK") {
                }
                    .show()
            }
        }


        giphyViewModel.giphyModel.observe(viewLifecycleOwner) {
            giphyImages.clear()
            giphyImages.addAll(it)
            adapter.notifyDataSetChanged()
            hideKeyboard()
        }
    }

    private fun initRecyclerView() {
        adapter = GiphyAdapter(activity?.applicationContext!!, giphyImages, object : ClickListener {
            override fun onClick(v: View?, position: Int) {
                val myIntent =
                    Intent(activity!!.applicationContext, DetailsSignActivity::class.java)

                myIntent.putExtra("imageUrl", giphyImages[position].images.original.url)
                myIntent.putExtra("networkImage", true)
                myIntent.putExtra("letter", giphyImages[position].title)
                myIntent.putExtra("type", giphyImages[position].source)

                startActivity(myIntent)
            }
        })

        binding.rvDogs.layoutManager = GridLayoutManager(activity?.applicationContext!!, 2)
        binding.rvDogs.adapter = adapter
    }

    private fun getRetrofitTranslate(): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://mymemory.translated.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchGiphy(query: String) {

        var getQuery = query
        if (!getLanguagePhone()) {

            CoroutineScope(Dispatchers.IO).launch {
                val call = getRetrofitTranslate().create(ApiInterfaceTranslate::class.java)
                    .getWordTranslated(
                        query,
                        "ES|EN",
                    )
                val translated = call.body()
                activity?.runOnUiThread {
                    if (call.isSuccessful) {
                        getQuery = translated?.responseData?.translatedText!!
                        fillRecyclerView(getQuery)
                    } else {
                        showError()
                    }
                    hideKeyboard()
                }
            }
        }

        fillRecyclerView(getQuery)
    }

    private fun hideKeyboard() {
        val imm =
            activity?.applicationContext!!.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }

    private fun showError() {
        Toast.makeText(
            activity?.applicationContext!!,
            getString(R.string.Error),
            Toast.LENGTH_SHORT
        )
            .show()
    }

    private fun fillRecyclerView(getQuery: String) {

        var cleanString = getQuery
        val match: Matcher = Pattern.compile("\\((.*?)\\)").matcher(getQuery)
        while (match.find()) {
            cleanString = match.group(1)
        }
        giphyViewModel.getGiphys("American Sign Language $cleanString")

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            searchGiphy(query.lowercase())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}