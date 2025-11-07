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
import com.example.proyectomoviles30.util.PreferenceHelper.set
import com.example.proyectomoviles30.util.PreferenceHelper.get


class Registro : AppCompatActivity() {

    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textViewIrInicioDeSesion = findViewById<TextView>(R.id.textViewIrInicioDeSesion)
        textViewIrInicioDeSesion.setOnClickListener {
            irInicioDeSesion()
        }

        val buttonIrInicioDeSesion = findViewById<Button>(R.id.buttonIrInicioDeSesion)
        buttonIrInicioDeSesion.setOnClickListener {
            performRegistro()
        }
    }

    // --- FUNCIÓN ACTUALIZADA ---
    private fun performRegistro() {
        val etNombre = findViewById<EditText>(R.id.editTextNombre)
        val etEmail = findViewById<EditText>(R.id.editTextEmail)
        val etPassword = findViewById<EditText>(R.id.editTextPassword)
        val etRepeatPassword = findViewById<EditText>(R.id.editTextRepeatPassword)

        val nombre = etNombre.text.toString()
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        val repeatPassword = etRepeatPassword.text.toString()

        // 1. Validar que los campos no estén vacíos
        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // 2. Validar que las contraseñas coincidan
        if (password != repeatPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        // --- ¡LÓGICA CORREGIDA! ---
        // 3. Validar que el email no exista
        // Simplemente revisamos si ya hay una contraseña guardada para ese email.
        val existingPass = preferences["${email}_pass", ""]

        // Usamos .toString() porque el helper puede devolver Any?
        if (existingPass.toString().isNotEmpty()) {
            // Si la contraseña NO está vacía, significa que el usuario ya existe
            Toast.makeText(this, "El correo electrónico ya está registrado", Toast.LENGTH_SHORT).show()
            return
        }

        // 4. Guardar usuario (Persistencia)
        // Si llegamos aquí, el email es nuevo y podemos guardarlo.
        preferences["${email}_name"] = nombre
        preferences["${email}_pass"] = password

        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
        irInicioDeSesion()
    }

    private fun irInicioDeSesion() {
        // 'InicioDeSesion' está en el mismo paquete, no necesita import
        val intent = Intent(this, InicioDeSesion::class.java)
        startActivity(intent)
    }
}