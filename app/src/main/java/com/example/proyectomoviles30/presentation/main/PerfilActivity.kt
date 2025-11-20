package com.example.proyectomoviles30.presentation.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.proyectomoviles30.R
import com.example.proyectomoviles30.presentation.ViewModelFactory

class PerfilActivity : AppCompatActivity() {

    private lateinit var viewModel: PerfilViewModel
    
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

        setupObservers()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        viewModel.cargarDatos()
    }

    private fun setupObservers() {
        viewModel.perfilData.observe(this) { data ->
            currentData = data
            updateUI(data)
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

        tvBienvenido.text = "¡Bienvenido, ${data.name}!"
        tvNombreCompleto.text = data.name
        tvEmail.text = data.email
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
            intent.putExtra("USER_EMAIL", it.email)
            intent.putExtra("USER_NAME", it.name)
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