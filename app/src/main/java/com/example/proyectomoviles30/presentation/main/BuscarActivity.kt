package com.example.proyectomoviles30.presentation.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.ViewModelProvider
import com.example.proyectomoviles30.R
import com.example.proyectomoviles30.presentation.ViewModelFactory

class BuscarActivity : AppCompatActivity() {

    private lateinit var editTextBusqueda: EditText
    private lateinit var recyclerViewResultados: RecyclerView
    private lateinit var textViewNoResultados: TextView
    private lateinit var subastasAdapter: SubastasAdapter
    
    private lateinit var viewModel: SubastasViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buscar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val factory = ViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory)[SubastasViewModel::class.java]

        val textViewIrMenuInicio = findViewById<TextView>(R.id.textViewIrMenuInicio)
        textViewIrMenuInicio.setOnClickListener {
            irMenuInicio()
        }

        editTextBusqueda = findViewById(R.id.editTextBusqueda)
        recyclerViewResultados = findViewById(R.id.recyclerViewResultados)
        textViewNoResultados = findViewById(R.id.textViewNoResultados)

        setupRecyclerView()
        setupObservers()
        setupSearchListener()
        
        // Cargamos subastas para poder buscar sobre ellas
        viewModel.cargarSubastas()
    }

    private fun setupRecyclerView() {
        subastasAdapter = SubastasAdapter(emptyList()) { subastaClickeada ->
            val intent = Intent(this, DetalleSubastaActivity::class.java)
            intent.putExtra("SUBASTA_ID", subastaClickeada.id)
            startActivity(intent)
        }
        recyclerViewResultados.adapter = subastasAdapter
        recyclerViewResultados.layoutManager = LinearLayoutManager(this)
    }

    private fun setupObservers() {
        // Observamos cambios en la lista de subastas (por si se cargan más o cambian)
    }

    private fun setupSearchListener() {
        editTextBusqueda.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtrarLista(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filtrarLista(query: String) {
        val listaCompleta = viewModel.subastas.value ?: emptyList()
        
        if (query.isBlank()) {
            recyclerViewResultados.visibility = View.GONE
            textViewNoResultados.visibility = View.GONE
            subastasAdapter.updateList(emptyList())
        } else {
            val resultados = listaCompleta.filter {
                it.titulo.contains(query, ignoreCase = true)
            }
            subastasAdapter.updateList(resultados)

            if (resultados.isEmpty()) {
                recyclerViewResultados.visibility = View.GONE
                textViewNoResultados.visibility = View.VISIBLE
            } else {
                recyclerViewResultados.visibility = View.VISIBLE
                textViewNoResultados.visibility = View.GONE
            }
        }
    }

    private fun irMenuInicio() {
        finish()
    }
}