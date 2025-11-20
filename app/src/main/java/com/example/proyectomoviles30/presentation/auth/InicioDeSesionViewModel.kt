package com.example.proyectomoviles30.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectomoviles30.domain.repository.UserRepository

class InicioDeSesionViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> get() = _loginState

    sealed class LoginState {
        object Initial : LoginState()
        object Loading : LoginState()
        object Success : LoginState()
        object SuccessGuest : LoginState()
        data class Error(val message: String) : LoginState()
    }

    fun checkSession() {
        if (userRepository.isLoggedIn()) {
            _loginState.value = LoginState.Success
        }
    }

    fun login(usernameOrEmail: String, password: String) {
        if (usernameOrEmail.isBlank() || password.isBlank()) {
            _loginState.value = LoginState.Error("Ingresa usuario y contraseña")
            return
        }

        val user = userRepository.getUser(usernameOrEmail)
        if (user != null && user.password == password) {
            userRepository.setLoggedIn(true)
            userRepository.setCurrentUserEmail(usernameOrEmail)
            _loginState.value = LoginState.Success
        } else {
            _loginState.value = LoginState.Error("Usuario o contraseña incorrectos")
        }
    }

    fun loginInvitado() {
        userRepository.setLoggedIn(true)
        userRepository.setCurrentUserEmail("invitado@mail.com")
        _loginState.value = LoginState.SuccessGuest
    }
}