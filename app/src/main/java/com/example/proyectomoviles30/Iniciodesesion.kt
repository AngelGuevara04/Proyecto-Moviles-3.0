package com.example.proyectomoviles30

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.content.Intent
import android.widget.Button


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

        val textViewIrRegistro = findViewById<TextView>(R.id.textViewIrRegistro)
        textViewIrRegistro.setOnClickListener {
            irRegistro()
        }

        val buttonIrMenuInicio = findViewById<Button>(R.id.buttonIrMenuInicio)
        buttonIrMenuInicio.setOnClickListener {
            irMenuInicio()
        }
    }
        private fun irRegistro() {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }
        private fun irMenuInicio() {
            val intent = Intent(this, MenuInicio::class.java)
            startActivity(intent)
        }
}