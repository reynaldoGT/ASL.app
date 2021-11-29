package com.neo.signLanguage.views.activities

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.neo.signLanguage.adapters.GiphyAdapter
import com.neo.signLanguage.databinding.ActivityGiphyBinding
import com.neo.signLanguage.models.Datum
import com.neo.signLanguage.provider.ApiInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GiphyActivity : Fragment(), SearchView.OnQueryTextListener {

    private var giphyImages = mutableListOf<Datum>()

    private var _binding: ActivityGiphyBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GiphyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Using the binding
        _binding = ActivityGiphyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBreed.setOnQueryTextListener(this)
        initRecyclerView()
        searchGiphy("sign with Robert")
    }

    private fun initRecyclerView() {
        adapter = GiphyAdapter(giphyImages, activity?.applicationContext!!)

        binding.rvDogs.layoutManager = GridLayoutManager(activity?.applicationContext!!, 2)
        binding.rvDogs.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.giphy.com/v1/gifs/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchGiphy(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiInterface::class.java)
                .getGiphyImage("Fx16au8XaXm3cKQb9QsK2RoOR7rZL7G9", "sign with Robert  $query")

            val giphys = call.body()

            activity?.runOnUiThread {
                if (call.isSuccessful) {
                    val images = giphys?.data ?: emptyList()
                    giphyImages.clear()
                    giphyImages.addAll(images)
                    adapter.notifyDataSetChanged()
                } else {
                    //show error
                    showError()
                }
                hideKeyboard()
            }
        }
    }

    private fun hideKeyboard() {
        val imm = activity?.applicationContext!!.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }

    private fun showError() {
        Toast.makeText(activity?.applicationContext!!, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            searchGiphy(query.toLowerCase())
        }
        return true;
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}