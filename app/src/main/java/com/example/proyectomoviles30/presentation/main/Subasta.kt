package com.example.proyectomoviles30.presentation.main

// --- Data Class para el modelo de datos ---
// Ahora vive en su propio archivo y puede ser usado por cualquier Activity.
data class Subasta(
    val id: String,
    val titulo: String,
    val pujaActual: Double,
    val tiempoRestante: String,
    val imageUrl: String // (Aquí iría la URL de la imagen)
)