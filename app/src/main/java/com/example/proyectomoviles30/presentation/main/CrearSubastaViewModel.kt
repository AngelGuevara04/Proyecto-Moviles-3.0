package com.example.proyectomoviles30.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectomoviles30.domain.model.Subasta
import com.example.proyectomoviles30.domain.repository.SubastaRepository
import java.util.UUID

class CrearSubastaViewModel(private val subastaRepository: SubastaRepository) : ViewModel() {

    private val _creationState = MutableLiveData<CreationState>()
    val creationState: LiveData<CreationState> get() = _creationState

    sealed class CreationState {
        object Initial : CreationState()
        object Loading : CreationState()
        object Success : CreationState()
        data class Error(val message: String) : CreationState()
    }

    fun publicarSubasta(titulo: String, puja: String, incremento: String, tiempo: String, imageUri: String?) {
        if (titulo.isBlank() || puja.isBlank() || incremento.isBlank() || tiempo.isBlank()) {
            _creationState.value = CreationState.Error("Por favor, completa todos los campos obligatorios")
            return
        }

        val pujaDouble = puja.toDoubleOrNull()
        val incrementoDouble = incremento.toDoubleOrNull()
        
        if (pujaDouble == null || incrementoDouble == null) {
            _creationState.value = CreationState.Error("La puja y el incremento deben ser números válidos")
            return
        }

        val newSubasta = Subasta(
            id = UUID.randomUUID().toString(),
            titulo = titulo,
            pujaActual = pujaDouble,
            incrementoMinimo = incrementoDouble,
            tiempoRestante = tiempo, // En un caso real, esto sería un timestamp calculado
            imageUrl = imageUri ?: ""
        )

        subastaRepository.addSubasta(newSubasta)
        _creationState.value = CreationState.Success
    }
}