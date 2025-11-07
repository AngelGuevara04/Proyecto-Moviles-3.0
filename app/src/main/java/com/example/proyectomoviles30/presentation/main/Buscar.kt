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


class Buscar : AppCompatActivity() {

    private lateinit var editTextBusqueda: EditText
    private lateinit var recyclerViewResultados: RecyclerView
    private lateinit var textViewNoResultados: TextView
    private lateinit var subastasAdapter: SubastasAdapter
    private var listaMostrada = mutableListOf<Subasta>()
    // con esta lista simulamos una base de datos
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

        //configuración del botón volver
        val textViewIrMenuInicio = findViewById<TextView>(R.id.textViewIrMenuInicio)
        textViewIrMenuInicio.setOnClickListener {
            irMenuInicio()
        }

        //inicializar vistas
        editTextBusqueda = findViewById(R.id.editTextBusqueda)
        recyclerViewResultados = findViewById(R.id.recyclerViewResultados)
        textViewNoResultados = findViewById(R.id.textViewNoResultados)

        cargarDatosCompletosDeEjemplo()
        setupRecyclerView()
        setupSearchListener()
    }

    private fun setupRecyclerView() {
        // Adaptador con la lista (inicialmente vacía)
        subastasAdapter = SubastasAdapter(listaMostrada) { subastaClickeada ->
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
        // limpia la lista actual
        listaMostrada.clear()

        if (query.isBlank()) {
            // si la búsqueda está vacía entonces ocultamos todo
            recyclerViewResultados.visibility = View.GONE
            textViewNoResultados.visibility = View.GONE
        } else {
            val resultados = listaCompletaDeSubastas.filter {
                it.titulo.contains(query, ignoreCase = true)
            }
            listaMostrada.addAll(resultados)

            // mostramos y ocultamos las vistas según los resultados relacionados
            if (listaMostrada.isEmpty()) {
                recyclerViewResultados.visibility = View.GONE
                textViewNoResultados.visibility = View.VISIBLE
            } else {
                recyclerViewResultados.visibility = View.VISIBLE
                textViewNoResultados.visibility = View.GONE
            }
        }

        // Se notifica al adaptador que los datos cambiaron
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