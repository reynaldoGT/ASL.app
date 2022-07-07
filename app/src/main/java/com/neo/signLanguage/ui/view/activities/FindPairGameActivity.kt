package com.neo.signLanguage.ui.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.neo.signLanguage.adapters.AdapterLettersGameRotate
import com.neo.signLanguage.adapters.ClickListener
import com.neo.signLanguage.databinding.ActivityFindPairGameBinding

import com.neo.signLanguage.ui.viewModel.GameViewModel
import com.orhanobut.logger.Logger

class FindPairGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFindPairGameBinding

    lateinit var adapter: AdapterLettersGameRotate
    private val model: GameViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindPairGameBinding.inflate(layoutInflater)
        model.getRandomToFindEquals(4)
        initRecyclerView()
        setContentView(binding.root)
    }

    private fun initRecyclerView() {

        model.randomGameLetters
            .observe(this) {
                adapter =
                    AdapterLettersGameRotate(
                        this,
                        it.data,
                        object : ClickListener {
                            override fun onClick(v: View?, position: Int) {
                                /*it.data[position]*/
                                /*Log.d("value", position.toString())
                                */
                                adapter.selectItem(position)

                            }
                        })
                binding.rvHistory.layoutManager = GridLayoutManager(this, 4)
                binding.rvHistory.adapter = adapter
            }
    }
}