package com.example.proyectomoviles30.presentation.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import android.content.Intent

import com.example.proyectomoviles30.R
import com.example.proyectomoviles30.util.PreferenceHelper
import com.example.proyectomoviles30.util.PreferenceHelper.get

class Perfil : AppCompatActivity() {

    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Asegúrate de que el layout se llama 'activity_perfil'
        setContentView(R.layout.activity_perfil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar listener para volver
        val textViewIrMenuInicio = findViewById<TextView>(R.id.textViewIrMenuInicio)
        textViewIrMenuInicio.setOnClickListener {
            irMenuInicio()
        }

        // Cargar y mostrar los datos del perfil
        cargarDatosPerfil()

        // Configurar listeners para los nuevos botones
        val btnEditarPerfil = findViewById<Button>(R.id.buttonEditarPerfil)
        btnEditarPerfil.setOnClickListener {
            // Aquí puedes iniciar una nueva Activity para editar el perfil
            Toast.makeText(this, "Ir a editar perfil...", Toast.LENGTH_SHORT).show()
        }

        /*
        // ELIMINADO PORQUE NO EXISTE EN EL XML
        val tvCerrarSesion = findViewById<TextView>(R.id.textViewCerrarSesion)
        tvCerrarSesion.setOnClickListener {
            // Aquí puedes poner la lógica para cerrar sesión
            Toast.makeText(this, "Cerrando sesión...", Toast.LENGTH_SHORT).show()
        }
        */
    }

    private fun cargarDatosPerfil() {
        // Obtener email y nombre (como ya lo tenías)
        val currentUserEmail = preferences["current_user_email", ""]
        val currentUserName = preferences["${currentUserEmail}_name", "Usuario"]

        // *** Cargar los datos adicionales (asumiendo claves de SharedPreferences) ***
        val currentUserTelefono = preferences["${currentUserEmail}_telefono", "No especificado"]
        val currentUserSexo = preferences["${currentUserEmail}_sexo", "No especificado"]
        val currentUserEdad = preferences["${currentUserEmail}_edad", "No especificado"]
        val currentUserMiembroDesde = preferences["${currentUserEmail}_miembro_desde", "Ene 2024"]

        // Buscar todos los TextView del layout
        val tvBienvenido = findViewById<TextView>(R.id.textViewBienvenido)
        val tvNombreCompleto = findViewById<TextView>(R.id.textViewNombreCompleto)
        val tvEmail = findViewById<TextView>(R.id.textViewEmail)
        val tvTelefono = findViewById<TextView>(R.id.textViewTelefono)
        val tvSexo = findViewById<TextView>(R.id.textViewSexo)
        val tvEdad = findViewById<TextView>(R.id.textViewEdad)
        val tvMiembroDesde = findViewById<TextView>(R.id.textViewMiembroDesde)

        // Asignar los datos a los TextView
        tvBienvenido.text = "Bienvenido, $currentUserName"
        tvNombreCompleto.text = currentUserName
        tvEmail.text = currentUserEmail
        tvTelefono.text = currentUserTelefono
        tvSexo.text = currentUserSexo
        tvEdad.text = currentUserEdad
        tvMiembroDesde.text = currentUserMiembroDesde
    }

    private fun irMenuInicio() {
        finish() // Cierra esta actividad y vuelve a la anterior
    }
}