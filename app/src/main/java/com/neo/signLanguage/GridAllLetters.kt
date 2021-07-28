package com.neo.signLanguage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridAllLetters : AppCompatActivity() {

    var lista: RecyclerView? = null
    var adaptador: AdapterLetters? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_grid_all_letters)

        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        toolBar?.setTitle(R.string.app_name)
        //para decirle que esta es la toolbar oficial

        setSupportActionBar(toolBar)
        val shared = Shared()
        val lettersArray = shared.getOnlyLettersArray()


        lista = findViewById(R.id.gridList)

        lista?.setHasFixedSize(true)

        layoutManager = GridLayoutManager(this, 2)
        lista?.layoutManager = layoutManager

        adaptador = AdapterLetters(lettersArray)

        lista?.adapter = adaptador

    }
}