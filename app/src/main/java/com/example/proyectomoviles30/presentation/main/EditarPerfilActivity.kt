package com.example.proyectomoviles30.presentation.main

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectomoviles30.R
import com.example.proyectomoviles30.util.PreferenceHelper
import com.example.proyectomoviles30.util.PreferenceHelper.set
import com.google.android.material.textfield.TextInputEditText

class EditarPerfilActivity : AppCompatActivity() {

    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    private lateinit var editTextNombre: TextInputEditText
    private lateinit var editTextTelefono: TextInputEditText
    private lateinit var editTextSexo: TextInputEditText
    private lateinit var editTextEdad: TextInputEditText

    private var currentUserEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar_perfil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextNombre = findViewById(R.id.editTextNombre)
        editTextTelefono = findViewById(R.id.editTextTelefono)
        editTextSexo = findViewById(R.id.editTextSexo)
        editTextEdad = findViewById(R.id.editTextEdad)
        val buttonGuardar: Button = findViewById(R.id.buttonGuardarCambios)
        val textViewCancelar: TextView = findViewById(R.id.textViewCancelar)

        currentUserEmail = intent.getStringExtra("USER_EMAIL")
        editTextNombre.setText(intent.getStringExtra("USER_NAME"))
        editTextTelefono.setText(intent.getStringExtra("USER_TELEFONO"))
        editTextSexo.setText(intent.getStringExtra("USER_SEXO"))
        editTextEdad.setText(intent.getStringExtra("USER_EDAD"))

        buttonGuardar.setOnClickListener {
            guardarCambios()
        }

        textViewCancelar.setOnClickListener {
            finish()
        }
    }

    private fun guardarCambios() {
        // Obtenemos los nuevos valores de los EditText
        val newName = editTextNombre.text.toString()
        val newTelefono = editTextTelefono.text.toString()
        val newSexo = editTextSexo.text.toString()
        val newEdad = editTextEdad.text.toString()

        if (currentUserEmail == null) {
            Toast.makeText(this, "Error: No se pudo identificar al usuario", Toast.LENGTH_SHORT).show()
            return
        }

        // Se guarda los nuevos valores en SharedPreferences
        // Usamos la extensión 'set' de PreferenceHelper
        preferences["${currentUserEmail}_name"] = newName
        preferences["${currentUserEmail}_telefono"] = newTelefono
        preferences["${currentUserEmail}_sexo"] = newSexo
        preferences["${currentUserEmail}_edad"] = newEdad

        // informa al usuario y cierra la pantalla
        Toast.makeText(this, "Perfil actualizado", Toast.LENGTH_SHORT).show()
        finish()
    }
}