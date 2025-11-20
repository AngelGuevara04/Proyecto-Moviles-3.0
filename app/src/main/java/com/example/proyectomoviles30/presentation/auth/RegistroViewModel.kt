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

    fun registrar(nombre: String, primerApellido: String, segundoApellido: String, email: String, pass: String, repeatPass: String) {
        if (nombre.isBlank() || primerApellido.isBlank() || email.isBlank() || pass.isBlank() || repeatPass.isBlank()) {
            _registroState.value = RegistroState.Error("Por favor, completa todos los campos requeridos")
            return
        }

        if (pass != repeatPass) {
            _registroState.value = RegistroState.Error("Las contraseñas no coinciden")
            return
        }

        val existingUser = userRepository.getUser(email)
        if (existingUser != null) {
            _registroState.value = RegistroState.Error("El nombre de usuario / correo ya está registrado")
            return
        }

        val newUser = User(email, nombre, primerApellido, if(segundoApellido.isBlank()) null else segundoApellido, pass)
        userRepository.saveUser(newUser)
        _registroState.value = RegistroState.Success
    }
}