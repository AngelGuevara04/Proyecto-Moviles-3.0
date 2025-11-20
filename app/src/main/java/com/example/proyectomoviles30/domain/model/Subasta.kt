package com.example.proyectomoviles30.domain.model

data class Subasta(
    val id: String,
    val titulo: String,
    val pujaActual: Double,
    val incrementoMinimo: Double,
    val tiempoRestante: String, // Podemos guardar la fecha límite como String ISO o Long
    val imageUrl: String
)