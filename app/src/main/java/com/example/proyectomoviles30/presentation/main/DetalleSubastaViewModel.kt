package com.example.proyectomoviles30.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectomoviles30.domain.model.Subasta
import com.example.proyectomoviles30.domain.repository.SubastaRepository
import com.example.proyectomoviles30.domain.repository.UserRepository

class DetalleSubastaViewModel(
    private val subastaRepository: SubastaRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _subasta = MutableLiveData<Subasta?>()
    val subasta: LiveData<Subasta?> get() = _subasta

    private val _isFavorito = MutableLiveData<Boolean>()
    val isFavorito: LiveData<Boolean> get() = _isFavorito

    private val _pujaState = MutableLiveData<PujaState>()
    val pujaState: LiveData<PujaState> get() = _pujaState

    sealed class PujaState {
        object Success : PujaState()
        data class Error(val message: String) : PujaState()
    }

    fun cargarSubasta(id: String) {
        val subastaEncontrada = subastaRepository.getSubastaById(id)
        _subasta.value = subastaEncontrada
        
        val username = userRepository.getCurrentUsername()
        if (username != null && subastaEncontrada != null) {
            _isFavorito.value = userRepository.isFavorito(username, id)
        }
    }

    fun toggleFavorito() {
        val subastaActual = _subasta.value ?: return
        val username = userRepository.getCurrentUsername() ?: return
        
        if (_isFavorito.value == true) {
            userRepository.removeFavorito(username, subastaActual.id)
            _isFavorito.value = false
        } else {
            userRepository.addFavorito(username, subastaActual.id)
            _isFavorito.value = true
        }
    }

    fun realizarPuja(montoOfertado: Double) {
        val subastaActual = _subasta.value
        if (subastaActual == null) {
            _pujaState.value = PujaState.Error("No se pudo encontrar la subasta")
            return
        }

        val pujaMinimaRequerida = subastaActual.pujaActual + subastaActual.incrementoMinimo

        if (montoOfertado < pujaMinimaRequerida) {
            _pujaState.value = PujaState.Error("La puja debe ser de al menos $${String.format("%,.2f", pujaMinimaRequerida)}")
            return
        }

        val exito = subastaRepository.actualizarPuja(subastaActual.id, montoOfertado)
        if (exito) {
            _pujaState.value = PujaState.Success
            // Recargamos los datos para refrescar la vista
            cargarSubasta(subastaActual.id)
        } else {
            _pujaState.value = PujaState.Error("Error al procesar la puja")
        }
    }
}