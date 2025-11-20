package com.example.proyectomoviles30.domain.model

data class User(
    val email: String,
    val name: String,
    val primerApellido: String,
    val segundoApellido: String?,
    val password: String
)