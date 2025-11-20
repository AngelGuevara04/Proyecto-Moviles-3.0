package com.example.proyectomoviles30.domain.repository

import com.example.proyectomoviles30.domain.model.Subasta

interface SubastaRepository {
    fun getSubastas(): List<Subasta>
    fun getSubastaById(id: String): Subasta?
    fun addSubasta(subasta: Subasta)
    fun actualizarPuja(id: String, nuevaPuja: Double): Boolean
    
    // Nuevo método para registrar quién puja
    fun registrarPuja(idSubasta: String, username: String, monto: Double)
    fun getSubastasPujadasPorUsuario(username: String): List<Subasta>
}