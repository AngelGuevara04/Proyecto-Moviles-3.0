package com.example.proyectomoviles30.presentation.main

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectomoviles30.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

// --- La Data Class 'Subasta' se eliminó de aquí ---
// --- La clase 'SubastasAdapter' se eliminó de aquí ---

class Subastas : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var subastasAdapter: SubastasAdapter
    private var listaDeSubastas = mutableListOf<Subasta>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_subastas) // Asegúrate que este es el nombre de tu XML
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- Configuración del Botón Volver ---
        val textViewIrMenuInicio = findViewById<TextView>(R.id.textViewIrMenuInicio)
        textViewIrMenuInicio.setOnClickListener {
            irMenuInicio()
        }

        // --- Configuración del RecyclerView ---
        setupRecyclerView()
        cargarDatosDeEjemplo()

        // --- Configuración del Floating Action Button (FAB) ---
        val fabCrear = findViewById<FloatingActionButton>(R.id.fabCrearSubasta)
        fabCrear.setOnClickListener {
            // Aquí iniciarías la Activity para crear una nueva subasta
            Toast.makeText(this, "Iniciando creación de subasta...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewSubastas)
        // Ahora 'SubastasAdapter' y 'Subasta' se importan de sus propios archivos
        subastasAdapter = SubastasAdapter(listaDeSubastas) { subastaClickeada ->
            // --- Manejar clic en un item de la lista ---
            // Aquí irías a la pantalla de detalle de la subasta
            Toast.makeText(this, "Viendo subasta de: ${subastaClickeada.titulo}", Toast.LENGTH_SHORT).show()
            // val intent = Intent(this, DetalleSubastaActivity::class.java)
            // intent.putExtra("SUBASTA_ID", subastaClickeada.id)
            // startActivity(intent)
        }
        recyclerView.adapter = subastasAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun cargarDatosDeEjemplo() {
        // En un caso real, estos datos vendrían de tu base de datos (Firebase, etc.)
        listaDeSubastas.add(Subasta("1", "Guitarra Eléctrica Fender", 8500.0, "2h 15m", ""))
        listaDeSubastas.add(Subasta("2", "Cuadro Abstracto Moderno", 4200.0, "1d 5h", ""))
        listaDeSubastas.add(Subasta("3", "Laptop Gamer Alienware", 22000.0, "0h 30m", ""))
        listaDeSubastas.add(Subasta("4", "Colección Monedas Antiguas", 1500.0, "5h 0m", ""))
        listaDeSubastas.add(Subasta("5", "Bicicleta de Montaña", 6800.0, "3d 1h", ""))

        // Notificar al adaptador que los datos han cambiado
        subastasAdapter.notifyDataSetChanged()
    }

    private fun irMenuInicio() {
        finish()
    }

    // --- El 'SubastasAdapter' ya no está aquí ---
}