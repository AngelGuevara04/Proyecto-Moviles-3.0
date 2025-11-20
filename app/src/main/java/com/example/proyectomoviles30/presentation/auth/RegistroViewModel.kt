package com.example.proyectomoviles30.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectomoviles30.domain.model.User
import com.example.proyectomoviles30.domain.repository.UserRepository

class RegistroViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _registroState = MutableLiveData<RegistroState>()
    val registroState: LiveData<RegistroState> get() = _registroState

    sealed class RegistroState {
        object Initial : RegistroState()
        object Loading : RegistroState()
        object Success : RegistroState()
        data class Error(val message: String) : RegistroState()
    }

    fun registrar(nombre: String, primerApellido: String, segundoApellido: String, username: String, pass: String, repeatPass: String) {
        if (nombre.isBlank() || primerApellido.isBlank() || username.isBlank() || pass.isBlank() || repeatPass.isBlank()) {
            _registroState.value = RegistroState.Error("Por favor, completa todos los campos requeridos")
            return
        }

        if (pass != repeatPass) {
            _registroState.value = RegistroState.Error("Las contraseñas no coinciden")
            return
        }

        // Verificación de usuario existente
        val existingUser = userRepository.getUser(username)
        if (existingUser != null) {
            _registroState.value = RegistroState.Error("El nombre de usuario '$username' ya está en uso. Por favor, elige otro.")
            return
        }

        val newUser = User(username, nombre, primerApellido, if(segundoApellido.isBlank()) null else segundoApellido, pass)
        userRepository.saveUser(newUser)
        _registroState.value = RegistroState.Success
    }
}