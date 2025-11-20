package com.example.proyectomoviles30.presentation.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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

class PerfilActivity : AppCompatActivity() {

    private lateinit var viewModel: PerfilViewModel
    private lateinit var recyclerViewFavoritos: RecyclerView
    private lateinit var favoritosAdapter: SubastasAdapter
    
    private var currentData: PerfilViewModel.PerfilData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val factory = ViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory)[PerfilViewModel::class.java]

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        viewModel.cargarDatos()
    }
    
    private fun setupRecyclerView() {
        recyclerViewFavoritos = findViewById(R.id.recyclerViewFavoritos)
        favoritosAdapter = SubastasAdapter(emptyList()) { subastaClickeada ->
            val intent = Intent(this, DetalleSubastaActivity::class.java)
            intent.putExtra("SUBASTA_ID", subastaClickeada.id)
            startActivity(intent)
        }
        recyclerViewFavoritos.adapter = favoritosAdapter
        recyclerViewFavoritos.layoutManager = LinearLayoutManager(this)
    }

    private fun setupObservers() {
        viewModel.perfilData.observe(this) { data ->
            currentData = data
            updateUI(data)
        }
        
        viewModel.favoritos.observe(this) { favs ->
            favoritosAdapter.updateList(favs)
        }
    }
    
    private fun updateUI(data: PerfilViewModel.PerfilData) {
        val tvBienvenido = findViewById<TextView>(R.id.textViewBienvenido)
        val tvNombreCompleto = findViewById<TextView>(R.id.textViewNombreCompleto)
        val tvEmail = findViewById<TextView>(R.id.textViewEmail)
        val tvTelefono = findViewById<TextView>(R.id.textViewTelefono)
        val tvSexo = findViewById<TextView>(R.id.textViewSexo)
        val tvEdad = findViewById<TextView>(R.id.textViewEdad)
        val tvMiembroDesde = findViewById<TextView>(R.id.textViewMiembroDesde)

        tvBienvenido.text = "¡Bienvenido, ${data.rawName}!" // Usamos solo el nombre pila para la bienvenida
        tvNombreCompleto.text = data.fullName // Nombre completo con apellidos
        tvEmail.text = data.username
        tvTelefono.text = data.telefono
        tvSexo.text = data.sexo
        tvEdad.text = data.edad
        tvMiembroDesde.text = data.miembroDesde
    }

    private fun setupListeners() {
        val textViewIrMenuInicio = findViewById<TextView>(R.id.textViewIrMenuInicio)
        textViewIrMenuInicio.setOnClickListener {
            irMenuInicio()
        }

        val btnEditarPerfil = findViewById<Button>(R.id.buttonEditarPerfil)
        btnEditarPerfil.setOnClickListener {
            irAEditarPerfil()
        }
    }

    private fun irAEditarPerfil() {
        val intent = Intent(this, EditarPerfilActivity::class.java)
        currentData?.let {
            intent.putExtra("USER_EMAIL", it.username) // Identifier
            intent.putExtra("USER_NAME", it.rawName)
            intent.putExtra("USER_PRIMER_APELLIDO", it.primerApellido)
            intent.putExtra("USER_SEGUNDO_APELLIDO", it.segundoApellido)
            intent.putExtra("USER_TELEFONO", it.telefono)
            intent.putExtra("USER_SEXO", it.sexo)
            intent.putExtra("USER_EDAD", it.edad)
        }
        startActivity(intent)
    }

    private fun irMenuInicio() {
        finish()
    }
}