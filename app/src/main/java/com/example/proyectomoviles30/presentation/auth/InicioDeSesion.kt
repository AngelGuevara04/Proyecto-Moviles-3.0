package com.example.proyectomoviles30.presentation.auth

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.example.proyectomoviles30.R
import com.example.proyectomoviles30.util.PreferenceHelper
import com.example.proyectomoviles30.util.PreferenceHelper.get
import com.example.proyectomoviles30.util.PreferenceHelper.set
import com.example.proyectomoviles30.presentation.main.MenuInicio

class InicioDeSesion : AppCompatActivity() {

    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_iniciodesesion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (preferences["session", false])
            irMenuInicio(true)

        val textViewIrRegistro = findViewById<TextView>(R.id.textViewIrRegistro)
        textViewIrRegistro.setOnClickListener {
            irRegistro()
        }

        val buttonIrMenuInicio = findViewById<Button>(R.id.buttonIrMenuInicio)
        buttonIrMenuInicio.setOnClickListener {
            performLogin()
        }

        val buttonIrMenuInicioInvitado = findViewById<Button>(R.id.buttonIrMenuInicioInvitado)
        buttonIrMenuInicioInvitado.setOnClickListener {
            irMenuInicioInvitado()
        }
    }
    private fun irRegistro() {
        val intent = Intent(this, Registro::class.java)
        startActivity(intent)
    }

    private fun performLogin() {
        val etEmail = findViewById<EditText>(R.id.editTextEmail)
        val etPassword = findViewById<EditText>(R.id.editTextPassword)

        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Ingresa correo y contraseña", Toast.LENGTH_SHORT).show()
            return
        }


        // Busca las contraseñas guardadas para ese email
        val savedPass = preferences["${email}_pass", ""]

        // Comparamos
        if (savedPass.toString() == password && password.isNotEmpty()) {
            createSessionPreference()
            preferences["current_user_email"] = email

            irMenuInicio(false)
        } else {
            Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun irMenuInicio(isSessionActive: Boolean) {
        val intent = Intent(this, MenuInicio::class.java)
        startActivity(intent)
        if (!isSessionActive) {
            finish()
        }
    }

    private fun irMenuInicioInvitado() {
        createSessionPreference()
        preferences["current_user_email"] = "invitado@mail.com"
        preferences["invitado@mail.com_name"] = "Invitado"

        irMenuInicio(false)
    }

    private fun createSessionPreference() {
        preferences["session"] = true
    }
}