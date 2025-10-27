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
import com.example.proyectomoviles30.util.PreferenceHelper.get
import com.example.proyectomoviles30.util.PreferenceHelper.set


class Iniciodesesion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_iniciodesesion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val preferences = PreferenceHelper.defaultPrefs(this)
        if (preferences["session", false])
            irMenuInicio()

        val textViewIrRegistro = findViewById<TextView>(R.id.textViewIrRegistro)
        textViewIrRegistro.setOnClickListener {
            irRegistro()
        }

        val buttonIrMenuInicio = findViewById<Button>(R.id.buttonIrMenuInicio)
        buttonIrMenuInicio.setOnClickListener {
            irMenuInicio()
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
        private fun irMenuInicio() {
            val intent = Intent(this, MenuInicio::class.java)
            startActivity(intent)
            finish()
        }

        private fun irMenuInicioInvitado() {
            val intent = Intent(this, MenuInicio::class.java)
            createSessionPreference()
            startActivity(intent)
            finish()
        }

        private fun createSessionPreference() {
            val preferences = PreferenceHelper.defaultPrefs(this)
            preferences["session"] = true
        }

}