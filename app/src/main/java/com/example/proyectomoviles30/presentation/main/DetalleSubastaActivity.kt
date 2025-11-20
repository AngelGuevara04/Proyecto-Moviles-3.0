package com.example.proyectomoviles30.presentation.main

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.proyectomoviles30.R
import com.example.proyectomoviles30.domain.model.Subasta
import com.example.proyectomoviles30.presentation.ViewModelFactory
import com.google.android.material.button.MaterialButton

class DetalleSubastaActivity : AppCompatActivity() {

    private lateinit var viewModel: DetalleSubastaViewModel
    private lateinit var btnFavorito: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_subasta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val subastaId = intent.getStringExtra("SUBASTA_ID")
        if (subastaId == null) {
            finish()
            return
        }

        val factory = ViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory)[DetalleSubastaViewModel::class.java]

        btnFavorito = findViewById(R.id.buttonFavorito)

        setupObservers()
        setupListeners()
        
        viewModel.cargarSubasta(subastaId)
    }

    private fun setupObservers() {
        viewModel.subasta.observe(this) { subasta ->
            if (subasta != null) {
                updateUI(subasta)
            } else {
                Toast.makeText(this, "Subasta no encontrada", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        viewModel.isFavorito.observe(this) { isFav ->
            if (isFav) {
                btnFavorito.text = "Quitar de Favoritos"
                btnFavorito.setIconResource(android.R.drawable.btn_star_big_on)
            } else {
                btnFavorito.text = "Agregar a Favoritos"
                btnFavorito.setIconResource(android.R.drawable.btn_star_big_off)
            }
        }

        viewModel.pujaState.observe(this) { state ->
            when (state) {
                is DetalleSubastaViewModel.PujaState.Success -> {
                    Toast.makeText(this, "¡Puja realizada con éxito!", Toast.LENGTH_SHORT).show()
                }
                is DetalleSubastaViewModel.PujaState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateUI(subasta: Subasta) {
        findViewById<TextView>(R.id.textViewTitulo).text = subasta.titulo
        findViewById<TextView>(R.id.textViewPujaActual).text = String.format("$%,.2f", subasta.pujaActual)
        findViewById<TextView>(R.id.textViewTiempo).text = "Cierra: ${subasta.tiempoRestante}"
        
        val imageView = findViewById<ImageView>(R.id.imageViewProducto)
        if (subasta.imageUrl.isNotEmpty()) {
            try {
                // Tomamos permiso persistente si es posible (para casos donde se abre directo desde favoritos/recientes y no se tomó al crear)
                // Aunque lo ideal es tomarlo al crear, esto es un fallback seguro.
                val uri = Uri.parse(subasta.imageUrl)
                try {
                    contentResolver.takePersistableUriPermission(uri, android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)
                } catch (e: SecurityException) {
                    // Si no podemos tomar persistencia (ya la tenemos o no se puede), ignoramos y probamos cargar
                }
                imageView.setImageURI(uri)
            } catch (e: Exception) {
                // Si falla la carga de la imagen, no crasheamos la app
                imageView.setImageResource(android.R.drawable.ic_menu_gallery) // Imagen por defecto
            }
        }
    }

    private fun setupListeners() {
        btnFavorito.setOnClickListener {
            viewModel.toggleFavorito()
        }
        
        findViewById<Button>(R.id.buttonPujar).setOnClickListener {
            mostrarDialogoPujar()
        }
    }

    private fun mostrarDialogoPujar() {
        val subastaActual = viewModel.subasta.value ?: return
        
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Realizar una puja")
        
        val minPuja = subastaActual.pujaActual + subastaActual.incrementoMinimo
        builder.setMessage("La puja mínima es de $${String.format("%,.2f", minPuja)}")

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        builder.setView(input)

        builder.setPositiveButton("Pujar") { _, _ ->
            val montoStr = input.text.toString()
            if (montoStr.isNotEmpty()) {
                val monto = montoStr.toDoubleOrNull()
                if (monto != null) {
                    viewModel.realizarPuja(monto)
                } else {
                    Toast.makeText(this, "Monto inválido", Toast.LENGTH_SHORT).show()
                }
            }
        }
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
}