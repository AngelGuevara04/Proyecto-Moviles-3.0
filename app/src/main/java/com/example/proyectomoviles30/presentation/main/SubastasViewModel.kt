package com.example.proyectomoviles30.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectomoviles30.domain.model.Subasta
import com.example.proyectomoviles30.domain.repository.SubastaRepository

class SubastasViewModel(private val subastaRepository: SubastaRepository) : ViewModel() {

    private val _subastas = MutableLiveData<List<Subasta>>()
    val subastas: LiveData<List<Subasta>> get() = _subastas

    fun cargarSubastas() {
        _subastas.value = subastaRepository.getSubastas()
    }
}