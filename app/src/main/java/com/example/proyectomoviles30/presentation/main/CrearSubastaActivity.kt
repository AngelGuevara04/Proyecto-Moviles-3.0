package com.example.proyectomoviles30.presentation.main

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectomoviles30.R
import com.google.android.material.textfield.TextInputEditText

class CrearSubastaActivity : AppCompatActivity() {

    // --- Campos del formulario ---
    private lateinit var editTextTitulo: TextInputEditText
    private lateinit var editTextPujaInicial: TextInputEditText
    private lateinit var editTextTiempo: TextInputEditText
    private lateinit var buttonSubirImagen: Button
    private lateinit var buttonPublicar: Button
    private lateinit var textViewCancelar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crear_subasta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- Encontrar Vistas ---
        editTextTitulo = findViewById(R.id.editTextTitulo)
        editTextPujaInicial = findViewById(R.id.editTextPujaInicial)
        editTextTiempo = findViewById(R.id.editTextTiempo)
        buttonSubirImagen = findViewById(R.id.buttonSubirImagen)
        buttonPublicar = findViewById(R.id.buttonPublicarSubasta)
        textViewCancelar = findViewById(R.id.textViewCancelar)

        // --- Configurar Listeners ---
        buttonPublicar.setOnClickListener {
            publicarSubasta()
        }

        textViewCancelar.setOnClickListener {
            finish() // Cierra la actividad
        }

        buttonSubirImagen.setOnClickListener {
            // Lógica futura para subir imagen
            Toast.makeText(this, "Función 'Subir Imagen' no implementada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun publicarSubasta() {
        val titulo = editTextTitulo.text.toString()
        val puja = editTextPujaInicial.text.toString()
        val tiempo = editTextTiempo.text.toString()

        // Validación simple
        if (titulo.isBlank() || puja.isBlank() || tiempo.isBlank()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // --- Simulación de guardado ---
        // En un futuro, aquí es donde guardarías los datos en Firebase
        // y NO en SharedPreferences, porque es una lista que crece.

        Toast.makeText(this, "Subasta '$titulo' publicada (simulación)", Toast.LENGTH_LONG).show()

        // Cierra esta actividad y vuelve a la pantalla de Subastas
        finish()
    }
}