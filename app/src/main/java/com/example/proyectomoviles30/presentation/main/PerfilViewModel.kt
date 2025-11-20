package com.example.proyectomoviles30.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectomoviles30.domain.repository.UserRepository

class PerfilViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _perfilData = MutableLiveData<PerfilData>()
    val perfilData: LiveData<PerfilData> get() = _perfilData

    data class PerfilData(
        val name: String,
        val email: String,
        val telefono: String,
        val sexo: String,
        val edad: String,
        val miembroDesde: String
    )

    fun cargarDatos() {
        val email = userRepository.getCurrentUserEmail()
        if (email != null) {
            val data = userRepository.getUserProfileData(email)
            _perfilData.value = PerfilData(
                name = data["name"] ?: "",
                email = email,
                telefono = data["telefono"] ?: "",
                sexo = data["sexo"] ?: "",
                edad = data["edad"] ?: "",
                miembroDesde = data["miembroDesde"] ?: ""
            )
        }
    }
}