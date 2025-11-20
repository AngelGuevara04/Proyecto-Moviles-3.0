package com.example.proyectomoviles30.data.repository

import android.content.Context
import com.example.proyectomoviles30.domain.model.User
import com.example.proyectomoviles30.domain.repository.UserRepository
import com.example.proyectomoviles30.util.PreferenceHelper
import com.example.proyectomoviles30.util.PreferenceHelper.get
import com.example.proyectomoviles30.util.PreferenceHelper.set

class UserRepositoryImpl(private val context: Context) : UserRepository {

    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(context)
    }

    override fun getUser(username: String): User? {
        val password = preferences["${username}_pass", ""]
        val name = preferences["${username}_name", ""]
        val primerApellido = preferences["${username}_primerApellido", ""]
        val segundoApellido = preferences["${username}_segundoApellido", ""]
        
        if (password.isNotEmpty()) {
            return User(username, name, primerApellido, if(segundoApellido.isNotEmpty()) segundoApellido else null, password)
        }
        return null
    }

    override fun saveUser(user: User) {
        // Usamos el username como ID unico
        preferences["${user.username}_name"] = user.name
        preferences["${user.username}_primerApellido"] = user.primerApellido
        preferences["${user.username}_segundoApellido"] = user.segundoApellido ?: ""
        preferences["${user.username}_pass"] = user.password
    }

    override fun isLoggedIn(): Boolean {
        return preferences["session", false]
    }

    override fun setLoggedIn(isLoggedIn: Boolean) {
        preferences["session"] = isLoggedIn
    }

    override fun getCurrentUsername(): String? {
        return preferences["current_username", ""]
    }
    
    override fun setCurrentUsername(username: String) {
        preferences["current_username"] = username
    }

    override fun getUserProfileData(username: String): Map<String, String> {
        val telefono = preferences["${username}_telefono", "No especificado"]
        val sexo = preferences["${username}_sexo", "No especificado"]
        val edad = preferences["${username}_edad", "No especificado"]
        val miembroDesde = preferences["${username}_miembro_desde", "Ene 2024"]
        
        val name = preferences["${username}_name", "Usuario"]
        val primerApellido = preferences["${username}_primerApellido", ""]
        val segundoApellido = preferences["${username}_segundoApellido", ""]
        
        // Para mostrar el nombre completo en el Perfil
        val fullName = if (primerApellido.isNotEmpty()) {
            "$name $primerApellido" + (if (segundoApellido.isNotEmpty()) " $segundoApellido" else "")
        } else {
            name
        }

        // Retornamos también los campos individuales para poder editar
        return mapOf(
            "fullName" to fullName,
            "rawName" to name,
            "primerApellido" to primerApellido,
            "segundoApellido" to segundoApellido,
            "telefono" to telefono,
            "sexo" to sexo,
            "edad" to edad,
            "miembroDesde" to miembroDesde
        )
    }

    override fun updateUserProfile(
        username: String, 
        name: String, 
        primerApellido: String, 
        segundoApellido: String, 
        telefono: String, 
        sexo: String, 
        edad: String
    ) {
        preferences["${username}_name"] = name
        preferences["${username}_primerApellido"] = primerApellido
        preferences["${username}_segundoApellido"] = segundoApellido
        preferences["${username}_telefono"] = telefono
        preferences["${username}_sexo"] = sexo
        preferences["${username}_edad"] = edad
    }

    // Favoritos Implementation
    override fun getFavoritos(username: String): List<String> {
        val favsString = preferences["${username}_favoritos", ""]
        if (favsString.isEmpty()) return emptyList()
        return favsString.split(",").filter { it.isNotEmpty() }
    }

    override fun addFavorito(username: String, subastaId: String) {
        val currentFavs = getFavoritos(username).toMutableList()
        if (!currentFavs.contains(subastaId)) {
            currentFavs.add(subastaId)
            saveFavoritos(username, currentFavs)
        }
    }

    override fun removeFavorito(username: String, subastaId: String) {
        val currentFavs = getFavoritos(username).toMutableList()
        if (currentFavs.remove(subastaId)) {
            saveFavoritos(username, currentFavs)
        }
    }
    
    override fun isFavorito(username: String, subastaId: String): Boolean {
        return getFavoritos(username).contains(subastaId)
    }

    private fun saveFavoritos(username: String, list: List<String>) {
        val favsString = list.joinToString(",")
        preferences["${username}_favoritos"] = favsString
    }
}