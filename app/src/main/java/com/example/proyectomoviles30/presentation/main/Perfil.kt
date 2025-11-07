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

    // Variables para guardar los datos cargados
    private var currentUserEmail: String = ""
    private var currentUserName: String = ""
    private var currentUserTelefono: String = ""
    private var currentUserSexo: String = ""
    private var currentUserEdad: String = ""
    private var currentUserMiembroDesde: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // listener para volver
        val textViewIrMenuInicio = findViewById<TextView>(R.id.textViewIrMenuInicio)
        textViewIrMenuInicio.setOnClickListener {
            irMenuInicio()
        }

        // listeners para el botón de editar
        val btnEditarPerfil = findViewById<Button>(R.id.buttonEditarPerfil)
        btnEditarPerfil.setOnClickListener {
            irAEditarPerfil()
        }
    }

    override fun onResume() {
        super.onResume()
        // Cargar y mostrar los datos del perfil cada vez que la pantalla se muestra
        cargarDatosPerfil()
    }

    private fun cargarDatosPerfil() {
        // obtener email y nombre
        currentUserEmail = preferences["current_user_email", ""]
        currentUserName = preferences["${currentUserEmail}_name", "Usuario"]

        // cargar los demas datos
        currentUserTelefono = preferences["${currentUserEmail}_telefono", "No especificado"]
        currentUserSexo = preferences["${currentUserEmail}_sexo", "No especificado"]
        currentUserEdad = preferences["${currentUserEmail}_edad", "No especificado"]
        currentUserMiembroDesde = preferences["${currentUserEmail}_miembro_desde", "Ene 2024"]

        // Buscar todos los TextView del layout
        val tvBienvenido = findViewById<TextView>(R.id.textViewBienvenido)
        val tvNombreCompleto = findViewById<TextView>(R.id.textViewNombreCompleto)
        val tvEmail = findViewById<TextView>(R.id.textViewEmail)
        val tvTelefono = findViewById<TextView>(R.id.textViewTelefono)
        val tvSexo = findViewById<TextView>(R.id.textViewSexo)
        val tvEdad = findViewById<TextView>(R.id.textViewEdad)
        val tvMiembroDesde = findViewById<TextView>(R.id.textViewMiembroDesde)

        // Asignar los datos a los TextView
        tvBienvenido.text = "¡Bienvenido, $currentUserName!"
        tvNombreCompleto.text = currentUserName
        tvEmail.text = currentUserEmail
        tvTelefono.text = currentUserTelefono
        tvSexo.text = currentUserSexo
        tvEdad.text = currentUserEdad
        tvMiembroDesde.text = currentUserMiembroDesde
    }

    private fun irAEditarPerfil() {
        val intent = Intent(this, EditarPerfilActivity::class.java)
        // enviamos todos los datos actuales a la nueva Activity
        intent.putExtra("USER_EMAIL", currentUserEmail)
        intent.putExtra("USER_NAME", currentUserName)
        intent.putExtra("USER_TELEFONO", currentUserTelefono)
        intent.putExtra("USER_SEXO", currentUserSexo)
        intent.putExtra("USER_EDAD", currentUserEdad)
        startActivity(intent)
    }

    private fun irMenuInicio() {
        finish() // Cerramos esta actividad y vuelve a la anterior
    }
}