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
import com.example.proyectomoviles30.presentation.main.MenuInicioActivity

class InicioDeSesionActivity : AppCompatActivity() {

    private lateinit var viewModel: InicioDeSesionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_iniciodesesion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val factory = ViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory)[InicioDeSesionViewModel::class.java]

        setupObservers()
        
        // Check if already logged in
        viewModel.checkSession()

        setupListeners()
    }

    private fun setupObservers() {
        viewModel.loginState.observe(this) { state ->
            when (state) {
                is InicioDeSesionViewModel.LoginState.Success -> {
                    irMenuInicio(false)
                }
                is InicioDeSesionViewModel.LoginState.SuccessGuest -> {
                    irMenuInicio(false)
                }
                is InicioDeSesionViewModel.LoginState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun setupListeners() {
        val textViewIrRegistro = findViewById<TextView>(R.id.textViewIrRegistro)
        textViewIrRegistro.setOnClickListener {
            irRegistro()
        }

        val buttonIrMenuInicio = findViewById<Button>(R.id.buttonIrMenuInicio)
        buttonIrMenuInicio.setOnClickListener {
            val etEmail = findViewById<EditText>(R.id.editTextEmail)
            val etPassword = findViewById<EditText>(R.id.editTextPassword)
            
            // Pasamos el contenido del campo de email como "username" o "email" al viewmodel
            viewModel.login(etEmail.text.toString(), etPassword.text.toString())
        }

        val buttonIrMenuInicioInvitado = findViewById<Button>(R.id.buttonIrMenuInicioInvitado)
        buttonIrMenuInicioInvitado.setOnClickListener {
            viewModel.loginInvitado()
        }
    }

    private fun irRegistro() {
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }

    private fun irMenuInicio(isSessionActive: Boolean) {
        val intent = Intent(this, MenuInicioActivity::class.java)
        startActivity(intent)
        if (!isSessionActive) {
            finish()
        }
    }
}