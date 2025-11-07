package com.example.proyectomoviles30.presentation.main

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
import com.example.proyectomoviles30.R

// --- La Data Class 'Subasta' se eliminó de aquí ---
// (Ahora se importa desde 'Subasta.kt')

class Buscar : AppCompatActivity() {

    private lateinit var editTextBusqueda: EditText
    private lateinit var recyclerViewResultados: RecyclerView
    private lateinit var textViewNoResultados: TextView
    private lateinit var subastasAdapter: SubastasAdapter

    // Esta lista contendrá los resultados filtrados
    private var listaMostrada = mutableListOf<Subasta>()
    // Esta lista simula tu base de datos completa
    private var listaCompletaDeSubastas = mutableListOf<Subasta>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buscar)
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

        // --- Inicializar Vistas ---
        editTextBusqueda = findViewById(R.id.editTextBusqueda)
        recyclerViewResultados = findViewById(R.id.recyclerViewResultados)
        textViewNoResultados = findViewById(R.id.textViewNoResultados)

        // --- Configurar Lógica de Búsqueda ---
        cargarDatosCompletosDeEjemplo()
        setupRecyclerView()
        setupSearchListener()
    }

    private fun setupRecyclerView() {
        // Inicializa el adaptador con la lista (inicialmente vacía)
        // 'SubastasAdapter' y 'Subasta' ahora se importan automáticamente
        subastasAdapter = SubastasAdapter(listaMostrada) { subastaClickeada ->
            // --- Manejar clic en un item de la lista ---
            Toast.makeText(this, "Viendo subasta de: ${subastaClickeada.titulo}", Toast.LENGTH_SHORT).show()
            // val intent = Intent(this, DetalleSubastaActivity::class.java)
            // intent.putExtra("SUBASTA_ID", subastaClickeada.id)
            // startActivity(intent)
        }
        recyclerViewResultados.adapter = subastasAdapter
        recyclerViewResultados.layoutManager = LinearLayoutManager(this)
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
        // 1. Limpiar la lista actual
        listaMostrada.clear()

        if (query.isBlank()) {
            // Si la búsqueda está vacía, ocultar todo
            recyclerViewResultados.visibility = View.GONE
            textViewNoResultados.visibility = View.GONE
        } else {
            // Si hay texto, filtrar la lista completa
            val resultados = listaCompletaDeSubastas.filter {
                it.titulo.contains(query, ignoreCase = true)
            }
            listaMostrada.addAll(resultados)

            // 3. Mostrar/Ocultar vistas según los resultados
            if (listaMostrada.isEmpty()) {
                recyclerViewResultados.visibility = View.GONE
                textViewNoResultados.visibility = View.VISIBLE
            } else {
                recyclerViewResultados.visibility = View.VISIBLE
                textViewNoResultados.visibility = View.GONE
            }
        }

        // 4. Notificar al adaptador que los datos cambiaron
        subastasAdapter.notifyDataSetChanged()
    }

    private fun cargarDatosCompletosDeEjemplo() {
        // Esta es tu "base de datos" simulada
        listaCompletaDeSubastas.add(Subasta("1", "Guitarra Eléctrica Fender", 8500.0, "2h 15m", ""))
        listaCompletaDeSubastas.add(Subasta("2", "Cuadro Abstracto Moderno", 4200.0, "1d 5h", ""))
        listaCompletaDeSubastas.add(Subasta("3", "Laptop Gamer Alienware", 22000.0, "0h 30m", ""))
        listaCompletaDeSubastas.add(Subasta("4", "Colección Monedas Antiguas", 1500.0, "5h 0m", ""))
        listaCompletaDeSubastas.add(Subasta("5", "Bicicleta de Montaña", 6800.0, "3d 1h", ""))
        listaCompletaDeSubastas.add(Subasta("6", "Teclado Mecánico Corsair", 1200.0, "1h 10m", ""))
    }

    private fun irMenuInicio() {
        finish()
    }
}