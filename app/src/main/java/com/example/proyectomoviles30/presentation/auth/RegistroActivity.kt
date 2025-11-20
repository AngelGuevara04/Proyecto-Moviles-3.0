package com.example.proyectomoviles30.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.proyectomoviles30.R
import com.example.proyectomoviles30.presentation.ViewModelFactory

class RegistroActivity : AppCompatActivity() {

    private lateinit var viewModel: RegistroViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val factory = ViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory)[RegistroViewModel::class.java]

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.registroState.observe(this) { state ->
            when (state) {
                is RegistroViewModel.RegistroState.Success -> {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    irInicioDeSesion()
                }
                is RegistroViewModel.RegistroState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun setupListeners() {
        val textViewIrInicioDeSesion = findViewById<TextView>(R.id.textViewIrInicioDeSesion)
        textViewIrInicioDeSesion.setOnClickListener {
            irInicioDeSesion()
        }

        val buttonIrInicioDeSesion = findViewById<Button>(R.id.buttonIrInicioDeSesion)
        buttonIrInicioDeSesion.setOnClickListener {
            val etNombre = findViewById<EditText>(R.id.editTextNombre)
            val etPrimerApellido = findViewById<EditText>(R.id.editTextPrimerApellido)
            val etSegundoApellido = findViewById<EditText>(R.id.editTextSegundoApellido)
            val etEmail = findViewById<EditText>(R.id.editTextEmail) // Usado como Username
            val etPassword = findViewById<EditText>(R.id.editTextPassword)
            val etRepeatPassword = findViewById<EditText>(R.id.editTextRepeatPassword)

            viewModel.registrar(
                etNombre.text.toString(),
                etPrimerApellido.text.toString(),
                etSegundoApellido.text.toString(),
                etEmail.text.toString(),
                etPassword.text.toString(),
                etRepeatPassword.text.toString()
            )
        }
    }

    private fun irInicioDeSesion() {
        val intent = Intent(this, InicioDeSesionActivity::class.java)
        startActivity(intent)
        finish()
    }
}