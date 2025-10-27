package com.example.proyectomoviles30

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.content.Intent
import android.widget.Button



class Registro : AppCompatActivity() {
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
            irInicioDeSesion()
        }
    }
    private fun irInicioDeSesion() {
        val intent = Intent(this, Iniciodesesion::class.java)
        startActivity(intent)
    }
}