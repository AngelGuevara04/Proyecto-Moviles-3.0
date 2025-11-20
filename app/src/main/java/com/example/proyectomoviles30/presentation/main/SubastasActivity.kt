package com.example.proyectomoviles30.presentation.main

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectomoviles30.R
import com.example.proyectomoviles30.presentation.ViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SubastasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var subastasAdapter: SubastasAdapter
    private lateinit var viewModel: SubastasViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_subastas)
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

        setupRecyclerView()
        
        viewModel.subastas.observe(this) { subastas ->
            subastasAdapter.updateList(subastas)
        }

        val fabCrear = findViewById<FloatingActionButton>(R.id.fabCrearSubasta)
        fabCrear.setOnClickListener {
            val intent = Intent(this, CrearSubastaActivity::class.java)
            startActivity(intent)
        }
    }
    
    override fun onResume() {
        super.onResume()
        viewModel.cargarSubastas()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewSubastas)
        subastasAdapter = SubastasAdapter(emptyList()) { subastaClickeada ->
            val intent = Intent(this, DetalleSubastaActivity::class.java)
            intent.putExtra("SUBASTA_ID", subastaClickeada.id)
            startActivity(intent)
        }
        recyclerView.adapter = subastasAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun irMenuInicio() {
        finish()
    }
}