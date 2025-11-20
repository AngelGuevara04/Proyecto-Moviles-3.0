package com.example.proyectomoviles30.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectomoviles30.R
import com.example.proyectomoviles30.domain.model.Subasta
import com.example.proyectomoviles30.presentation.ViewModelFactory
import com.google.android.material.button.MaterialButton

class PerfilActivity : AppCompatActivity() {

    private lateinit var viewModel: PerfilViewModel
    private lateinit var recyclerViewListas: RecyclerView
    private lateinit var subastasAdapter: SubastasAdapter
    private lateinit var btnTabFavoritos: MaterialButton
    private lateinit var btnTabPujas: MaterialButton
    private lateinit var textViewListaVacia: TextView
    
    private var currentData: PerfilViewModel.PerfilData? = null
    
    // Estado de la UI
    private var isShowingFavoritos = true

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

        initViews()
        setupRecyclerView()
        setupObservers()
        setupListeners()
    }
    
    private fun initViews() {
        recyclerViewListas = findViewById(R.id.recyclerViewListas)
        btnTabFavoritos = findViewById(R.id.btnTabFavoritos)
        btnTabPujas = findViewById(R.id.btnTabPujas)
        textViewListaVacia = findViewById(R.id.textViewListaVacia)
    }

    override fun onResume() {
        super.onResume()
        viewModel.cargarDatos()
    }
    
    private fun setupRecyclerView() {
        subastasAdapter = SubastasAdapter(emptyList()) { subastaClickeada ->
            val intent = Intent(this, DetalleSubastaActivity::class.java)
            intent.putExtra("SUBASTA_ID", subastaClickeada.id)
            startActivity(intent)
        }
        recyclerViewListas.adapter = subastasAdapter
        recyclerViewListas.layoutManager = LinearLayoutManager(this)
    }

    private fun setupObservers() {
        viewModel.perfilData.observe(this) { data ->
            currentData = data
            updateUI(data)
        }
        
        viewModel.favoritos.observe(this) { favs ->
            if (isShowingFavoritos) {
                updateListUI(favs)
            }
        }
        
        viewModel.misPujas.observe(this) { pujas ->
            if (!isShowingFavoritos) {
                updateListUI(pujas)
            }
        }
    }
    
    private fun updateListUI(list: List<Subasta>) {
        subastasAdapter.updateList(list)
        if (list.isEmpty()) {
            textViewListaVacia.visibility = View.VISIBLE
            textViewListaVacia.text = if (isShowingFavoritos) "No tienes favoritos aún" else "No has realizado ninguna puja"
        } else {
            textViewListaVacia.visibility = View.GONE
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

        tvBienvenido.text = "¡Bienvenido, ${data.rawName}!"
        tvNombreCompleto.text = data.fullName
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
        
        btnTabFavoritos.setOnClickListener {
            cambiarPestana(true)
        }
        
        btnTabPujas.setOnClickListener {
            cambiarPestana(false)
        }
    }
    
    private fun cambiarPestana(mostrarFavoritos: Boolean) {
        isShowingFavoritos = mostrarFavoritos
        
        // Actualizar estilos de botones
        if (mostrarFavoritos) {
            // Favoritos activo (Filled), Pujas inactivo (Outlined)
            // Nota: Esto requiere tener definidos estilos específicos o manipular drawables, 
            // pero usaremos un truco simple con el estilo Material
             
            // Simplemente invertimos la lógica visual básica (o recargamos lista)
            // Para simplificar en código sin manipular estilos complejos dinámicamente:
            // Podríamos cambiar el background color o alpha
            btnTabFavoritos.alpha = 1.0f
            btnTabPujas.alpha = 0.5f
            
            val favs = viewModel.favoritos.value ?: emptyList()
            updateListUI(favs)
        } else {
            // Pujas activo
            btnTabFavoritos.alpha = 0.5f
            btnTabPujas.alpha = 1.0f
            
            val pujas = viewModel.misPujas.value ?: emptyList()
            updateListUI(pujas)
        }
    }

    private fun irAEditarPerfil() {
        val intent = Intent(this, EditarPerfilActivity::class.java)
        currentData?.let {
            intent.putExtra("USER_EMAIL", it.username)
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