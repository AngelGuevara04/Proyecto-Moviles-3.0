package com.example.proyectomoviles30.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectomoviles30.domain.repository.UserRepository

class EditarPerfilViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _updateState = MutableLiveData<UpdateState>()
    val updateState: LiveData<UpdateState> get() = _updateState

    sealed class UpdateState {
        object Initial : UpdateState()
        object Success : UpdateState()
        data class Error(val message: String) : UpdateState()
    }

    fun guardarCambios(
        username: String?, 
        name: String, 
        primerApellido: String, 
        segundoApellido: String, 
        telefono: String, 
        sexo: String, 
        edad: String
    ) {
        if (username == null) {
            _updateState.value = UpdateState.Error("Error: No se pudo identificar al usuario")
            return
        }
        
        if (name.isBlank() || primerApellido.isBlank()) {
            _updateState.value = UpdateState.Error("El nombre y el primer apellido son obligatorios")
            return
        }
        
        userRepository.updateUserProfile(username, name, primerApellido, segundoApellido, telefono, sexo, edad)
        _updateState.value = UpdateState.Success
    }
}