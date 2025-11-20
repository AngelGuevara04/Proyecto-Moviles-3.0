package com.example.proyectomoviles30.domain.repository

import com.example.proyectomoviles30.domain.model.User

interface UserRepository {
    fun getUser(email: String): User?
    fun saveUser(user: User)
    fun isLoggedIn(): Boolean
    fun setLoggedIn(isLoggedIn: Boolean)
    fun getCurrentUserEmail(): String?
    fun setCurrentUserEmail(email: String)
    
    // Métodos para perfil extendido
    fun getUserProfileData(email: String): Map<String, String>
    fun updateUserProfile(email: String, name: String, telefono: String, sexo: String, edad: String)
}