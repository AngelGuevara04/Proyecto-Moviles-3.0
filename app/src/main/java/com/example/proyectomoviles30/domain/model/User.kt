package com.example.proyectomoviles30.domain.model

data class User(
    val username: String,
    val name: String,
    val primerApellido: String,
    val segundoApellido: String?,
    val password: String
)