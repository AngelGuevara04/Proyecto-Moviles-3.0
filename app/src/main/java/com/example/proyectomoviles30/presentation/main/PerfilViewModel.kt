package com.example.proyectomoviles30.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectomoviles30.domain.model.Subasta
import com.example.proyectomoviles30.domain.repository.SubastaRepository
import com.example.proyectomoviles30.domain.repository.UserRepository

class PerfilViewModel(
    private val userRepository: UserRepository,
    private val subastaRepository: SubastaRepository
) : ViewModel() {

    private val _perfilData = MutableLiveData<PerfilData>()
    val perfilData: LiveData<PerfilData> get() = _perfilData
    
    private val _favoritos = MutableLiveData<List<Subasta>>()
    val favoritos: LiveData<List<Subasta>> get() = _favoritos

    data class PerfilData(
        val fullName: String,
        val rawName: String,
        val primerApellido: String,
        val segundoApellido: String,
        val username: String,
        val telefono: String,
        val sexo: String,
        val edad: String,
        val miembroDesde: String
    )

    fun cargarDatos() {
        val username = userRepository.getCurrentUsername()
        if (username != null) {
            val data = userRepository.getUserProfileData(username)
            _perfilData.value = PerfilData(
                fullName = data["fullName"] ?: "",
                rawName = data["rawName"] ?: "",
                primerApellido = data["primerApellido"] ?: "",
                segundoApellido = data["segundoApellido"] ?: "",
                username = username,
                telefono = data["telefono"] ?: "",
                sexo = data["sexo"] ?: "",
                edad = data["edad"] ?: "",
                miembroDesde = data["miembroDesde"] ?: ""
            )
            
            cargarFavoritos(username)
        }
    }
    
    private fun cargarFavoritos(username: String) {
        val favoritosIds = userRepository.getFavoritos(username)
        val subastas = subastaRepository.getSubastas()
        val favsList = subastas.filter { favoritosIds.contains(it.id) }
        _favoritos.value = favsList
    }
}