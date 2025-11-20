package com.example.proyectomoviles30.domain.repository

import com.example.proyectomoviles30.domain.model.Subasta

interface SubastaRepository {
    fun getSubastas(): List<Subasta>
    fun addSubasta(subasta: Subasta)
}