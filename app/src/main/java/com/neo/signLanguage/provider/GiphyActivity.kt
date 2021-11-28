package com.neo.signLanguage.provider

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.neo.signLanguage.adapters.GiphyAdapter
import com.neo.signLanguage.databinding.ActivityGiphyBinding
import com.neo.signLanguage.models.Datum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GiphyActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private var giphyImages = mutableListOf<Datum>()
    private lateinit var adapter: GiphyAdapter

    private lateinit var binding: ActivityGiphyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGiphyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchBreed.setOnQueryTextListener(this)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = GiphyAdapter(giphyImages, this)

        binding.rvDogs.layoutManager = GridLayoutManager(this, 2)
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

            runOnUiThread {
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
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
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