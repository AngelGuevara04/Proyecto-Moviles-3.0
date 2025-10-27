package com.example.proyectomoviles30

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.content.Intent
import android.widget.Button
import com.example.proyectomoviles30.util.PreferenceHelper
import com.example.proyectomoviles30.util.PreferenceHelper.set


class MenuInicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_inicio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textViewCerrarSesion = findViewById<TextView>(R.id.textViewCerrarSesion)
        textViewCerrarSesion.setOnClickListener {
            clearSessionPreference()
            irInicioDeSesion()
        }

        val buttonIrPerfil = findViewById<Button>(R.id.buttonIrPerfil)
        buttonIrPerfil.setOnClickListener {
            irPerfil()
        }

        val buttonIrBuscar = findViewById<Button>(R.id.buttonIrBuscar)
        buttonIrBuscar.setOnClickListener {
            irBuscar()
        }

        val buttonIrSubastas = findViewById<Button>(R.id.buttonIrSubastas)
        buttonIrSubastas.setOnClickListener {
            irSubastas()
        }
    }

    private fun irInicioDeSesion() {
        val intent = Intent(this, InicioDeSesion::class.java)
        startActivity(intent)
    }

    private fun clearSessionPreference() {
        val preferences = PreferenceHelper.defaultPrefs(this)
        preferences["session"] = false
    }

    private fun irPerfil() {
        val intent = Intent(this, Perfil::class.java)
        startActivity(intent)
    }

    private fun irBuscar() {
        val intent = Intent(this, Buscar::class.java)
        startActivity(intent)
    }

    private fun irSubastas() {
        val intent = Intent(this, Subastas::class.java)
        startActivity(intent)

    }
}