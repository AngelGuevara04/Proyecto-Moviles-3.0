package com.example.proyectomoviles30.domain.repository

import com.example.proyectomoviles30.domain.model.User

interface UserRepository {
    fun getUser(username: String): User?
    fun saveUser(user: User)
    fun isLoggedIn(): Boolean
    fun setLoggedIn(isLoggedIn: Boolean)
    fun getCurrentUsername(): String?
    fun setCurrentUsername(username: String)
    
    // Métodos para perfil extendido
    fun getUserProfileData(username: String): Map<String, String>
    
    // Actualizamos la firma para aceptar los nombres por separado
    fun updateUserProfile(
        username: String, 
        name: String, 
        primerApellido: String, 
        segundoApellido: String, 
        telefono: String, 
        sexo: String, 
        edad: String
    )
    
    // Favoritos
    fun getFavoritos(username: String): List<String> 
    fun addFavorito(username: String, subastaId: String)
    fun removeFavorito(username: String, subastaId: String)
    fun isFavorito(username: String, subastaId: String): Boolean
}