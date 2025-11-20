package com.example.proyectomoviles30.domain.repository

import com.example.proyectomoviles30.domain.model.Subasta

interface SubastaRepository {
    fun getSubastas(): List<Subasta>
    fun getSubastaById(id: String): Subasta?
    fun addSubasta(subasta: Subasta)
    fun actualizarPuja(id: String, nuevaPuja: Double): Boolean
}