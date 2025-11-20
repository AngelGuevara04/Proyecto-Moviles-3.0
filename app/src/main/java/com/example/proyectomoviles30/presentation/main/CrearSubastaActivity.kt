package com.example.proyectomoviles30.presentation.main

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.proyectomoviles30.R
import com.example.proyectomoviles30.presentation.ViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Calendar
import java.util.Locale

class CrearSubastaActivity : AppCompatActivity() {

    private lateinit var editTextTitulo: TextInputEditText
    private lateinit var editTextPujaInicial: TextInputEditText
    private lateinit var editTextIncremento: TextInputEditText
    private lateinit var editTextTiempo: TextInputEditText
    private lateinit var buttonSubirImagen: Button
    private lateinit var buttonPublicar: Button
    private lateinit var textViewCancelar: TextView
    private lateinit var imageViewPreview: ImageView

    private lateinit var viewModel: CrearSubastaViewModel
    
    private var selectedImageUri: Uri? = null
    
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            imageViewPreview.setImageURI(it)
            imageViewPreview.visibility = android.view.View.VISIBLE
            buttonSubirImagen.visibility = android.view.View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crear_subasta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val factory = ViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory)[CrearSubastaViewModel::class.java]

        initViews()
        setupFormatters()
        setupDatePiker()
        setupObservers()
        setupListeners()
    }

    private fun initViews() {
        editTextTitulo = findViewById(R.id.editTextTitulo)
        editTextPujaInicial = findViewById(R.id.editTextPujaInicial)
        editTextIncremento = findViewById(R.id.editTextIncremento)
        editTextTiempo = findViewById(R.id.editTextTiempo)
        buttonSubirImagen = findViewById(R.id.buttonSubirImagen)
        buttonPublicar = findViewById(R.id.buttonPublicarSubasta)
        textViewCancelar = findViewById(R.id.textViewCancelar)
        imageViewPreview = findViewById(R.id.imageViewPreview)
    }
    
    private fun setupFormatters() {
        // Añadimos TextWatchers para formatear de 3 en 3
        editTextPujaInicial.addTextChangedListener(NumberTextWatcher(editTextPujaInicial))
        editTextIncremento.addTextChangedListener(NumberTextWatcher(editTextIncremento))
    }
    
    private fun setupDatePiker() {
        editTextTiempo.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                
                val timePicker = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                    val formattedTime = String.format(Locale.getDefault(), "%04d-%02d-%02d %02d:%02d", selectedYear, selectedMonth + 1, selectedDay, selectedHour, selectedMinute)
                    editTextTiempo.setText(formattedTime)
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
                
                timePicker.show()

            }, year, month, day).show()
        }
    }

    private fun setupObservers() {
        viewModel.creationState.observe(this) { state ->
            when (state) {
                is CrearSubastaViewModel.CreationState.Success -> {
                    Toast.makeText(this, "Subasta publicada exitosamente", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is CrearSubastaViewModel.CreationState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun setupListeners() {
        buttonPublicar.setOnClickListener {
            val titulo = editTextTitulo.text.toString()
            // Removemos las comas para parsear el numero
            val puja = editTextPujaInicial.text.toString().replace(",", "")
            val incremento = editTextIncremento.text.toString().replace(",", "")
            val tiempo = editTextTiempo.text.toString()
            
            viewModel.publicarSubasta(titulo, puja, incremento, tiempo, selectedImageUri?.toString())
        }

        textViewCancelar.setOnClickListener {
            finish()
        }

        buttonSubirImagen.setOnClickListener {
            pickImage.launch("image/*")
        }
        
        imageViewPreview.setOnClickListener {
             pickImage.launch("image/*")
        }
    }
    
    // Clase interna para el formateo de numeros con comas
    class NumberTextWatcher(private val et: TextInputEditText) : TextWatcher {
        private var isUpdating = false
        private val decimalFormat = DecimalFormat("#,###.##", DecimalFormatSymbols(Locale.US))

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (isUpdating) {
                isUpdating = false
                return
            }

            var str = s.toString().replace(",", "")
            if (str.isNotEmpty()) {
                try {
                    val parsed = str.toDouble()
                    val formatted = decimalFormat.format(parsed)
                    
                    isUpdating = true
                    et.setText(formatted)
                    et.setSelection(formatted.length)
                } catch (e: NumberFormatException) {
                    // ignore
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }
}