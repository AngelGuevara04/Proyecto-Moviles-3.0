package com.example.proyectomoviles30.presentation.main

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.proyectomoviles30.R
import com.example.proyectomoviles30.presentation.ViewModelFactory
import com.google.android.material.textfield.TextInputEditText

class EditarPerfilActivity : AppCompatActivity() {

    private lateinit var editTextNombre: TextInputEditText
    private lateinit var editTextPrimerApellido: TextInputEditText
    private lateinit var editTextSegundoApellido: TextInputEditText
    private lateinit var editTextTelefono: TextInputEditText
    private lateinit var autoCompleteSexo: AutoCompleteTextView
    private lateinit var editTextEdad: TextInputEditText
    
    private lateinit var viewModel: EditarPerfilViewModel
    
    private var currentUsername: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar_perfil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val factory = ViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory)[EditarPerfilViewModel::class.java]

        initViews()
        setupSexoDropdown()
        populateData()
        setupObservers()
        setupListeners()
    }

    private fun initViews() {
        editTextNombre = findViewById(R.id.editTextNombre)
        editTextPrimerApellido = findViewById(R.id.editTextPrimerApellido)
        editTextSegundoApellido = findViewById(R.id.editTextSegundoApellido)
        editTextTelefono = findViewById(R.id.editTextTelefono)
        autoCompleteSexo = findViewById(R.id.autoCompleteSexo)
        editTextEdad = findViewById(R.id.editTextEdad)
    }

    private fun setupSexoDropdown() {
        val items = listOf("Hombre", "Mujer")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items)
        autoCompleteSexo.setAdapter(adapter)
    }

    private fun populateData() {
        currentUsername = intent.getStringExtra("USER_EMAIL") // Es el username realmente
        
        editTextNombre.setText(intent.getStringExtra("USER_NAME"))
        editTextPrimerApellido.setText(intent.getStringExtra("USER_PRIMER_APELLIDO"))
        editTextSegundoApellido.setText(intent.getStringExtra("USER_SEGUNDO_APELLIDO"))
        editTextTelefono.setText(intent.getStringExtra("USER_TELEFONO"))
        autoCompleteSexo.setText(intent.getStringExtra("USER_SEXO"), false)
        editTextEdad.setText(intent.getStringExtra("USER_EDAD"))
    }

    private fun setupObservers() {
        viewModel.updateState.observe(this) { state ->
            when (state) {
                is EditarPerfilViewModel.UpdateState.Success -> {
                    Toast.makeText(this, "Perfil actualizado", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is EditarPerfilViewModel.UpdateState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun setupListeners() {
        val buttonGuardar: Button = findViewById(R.id.buttonGuardarCambios)
        val textViewCancelar: TextView = findViewById(R.id.textViewCancelar)

        buttonGuardar.setOnClickListener {
             viewModel.guardarCambios(
                 currentUsername,
                 editTextNombre.text.toString(),
                 editTextPrimerApellido.text.toString(),
                 editTextSegundoApellido.text.toString(),
                 editTextTelefono.text.toString(),
                 autoCompleteSexo.text.toString(),
                 editTextEdad.text.toString()
             )
        }

        textViewCancelar.setOnClickListener {
            finish()
        }
    }
}