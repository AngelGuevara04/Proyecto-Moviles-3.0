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

    override fun getUser(email: String): User? {
        val password = preferences["${email}_pass", ""]
        val name = preferences["${email}_name", ""]
        val primerApellido = preferences["${email}_primerApellido", ""]
        val segundoApellido = preferences["${email}_segundoApellido", ""]
        
        if (password.isNotEmpty()) {
            return User(email, name, primerApellido, if(segundoApellido.isNotEmpty()) segundoApellido else null, password)
        }
        return null
    }

    override fun saveUser(user: User) {
        // Usamos el email como nombre de usuario unico (ID)
        preferences["${user.email}_name"] = user.name
        preferences["${user.email}_primerApellido"] = user.primerApellido
        preferences["${user.email}_segundoApellido"] = user.segundoApellido ?: ""
        preferences["${user.email}_pass"] = user.password
    }

    override fun isLoggedIn(): Boolean {
        return preferences["session", false]
    }

    override fun setLoggedIn(isLoggedIn: Boolean) {
        preferences["session"] = isLoggedIn
    }

    override fun getCurrentUserEmail(): String? {
        return preferences["current_user_email", ""]
    }
    
    override fun setCurrentUserEmail(email: String) {
        preferences["current_user_email"] = email
    }

    override fun getUserProfileData(email: String): Map<String, String> {
        val telefono = preferences["${email}_telefono", "No especificado"]
        val sexo = preferences["${email}_sexo", "No especificado"]
        val edad = preferences["${email}_edad", "No especificado"]
        val miembroDesde = preferences["${email}_miembro_desde", "Ene 2024"]
        val name = preferences["${email}_name", "Usuario"]
        val primerApellido = preferences["${email}_primerApellido", ""]
        val segundoApellido = preferences["${email}_segundoApellido", ""]
        
        val fullName = if (primerApellido.isNotEmpty()) {
            "$name $primerApellido" + (if (segundoApellido.isNotEmpty()) " $segundoApellido" else "")
        } else {
            name
        }

        return mapOf(
            "name" to fullName,
            "telefono" to telefono,
            "sexo" to sexo,
            "edad" to edad,
            "miembroDesde" to miembroDesde
        )
    }

    override fun updateUserProfile(email: String, name: String, telefono: String, sexo: String, edad: String) {
        // Nota: En editar perfil simplificado estamos asumiendo que "name" es el nombre completo o solo el primer nombre.
        // Si quisiéramos editar apellidos por separado, tendríamos que cambiar la pantalla de editar perfil también.
        // Por ahora, guardaremos el "name" recibido en el campo de nombre.
        preferences["${email}_name"] = name
        preferences["${email}_telefono"] = telefono
        preferences["${email}_sexo"] = sexo
        preferences["${email}_edad"] = edad
    }
}