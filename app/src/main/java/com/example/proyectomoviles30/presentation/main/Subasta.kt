package com.example.proyectomoviles30.presentation.main

data class Subasta(
    val id: String,
    val titulo: String,
    val pujaActual: Double,
    val tiempoRestante: String,
    val imageUrl: String // en el caso de que se agregara
)