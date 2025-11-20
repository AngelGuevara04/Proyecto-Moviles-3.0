package com.example.proyectomoviles30.data.repository

import com.example.proyectomoviles30.domain.model.Subasta
import com.example.proyectomoviles30.domain.repository.SubastaRepository

class SubastaRepositoryImpl : SubastaRepository {

    companion object {
        private val listaDeSubastas = mutableListOf<Subasta>(
            Subasta("1", "Guitarra Eléctrica Fender", 8500.0, 100.0, "2024-12-31 23:59", ""),
            Subasta("2", "Cuadro Abstracto Moderno", 4200.0, 50.0, "2024-11-15 12:00", ""),
            Subasta("3", "Laptop Gamer Alienware", 22000.0, 500.0, "2024-10-30 18:00", ""),
            Subasta("4", "Colección Monedas Antiguas", 1500.0, 20.0, "2024-11-01 09:00", ""),
            Subasta("5", "Bicicleta de Montaña", 6800.0, 150.0, "2024-12-01 10:00", "")
        )
    }

    override fun getSubastas(): List<Subasta> {
        return listaDeSubastas
    }

    override fun getSubastaById(id: String): Subasta? {
        return listaDeSubastas.find { it.id == id }
    }

    override fun addSubasta(subasta: Subasta) {
        listaDeSubastas.add(subasta)
    }

    override fun actualizarPuja(id: String, nuevaPuja: Double): Boolean {
        val index = listaDeSubastas.indexOfFirst { it.id == id }
        if (index != -1) {
            val subastaAntigua = listaDeSubastas[index]
            val subastaActualizada = subastaAntigua.copy(pujaActual = nuevaPuja)
            listaDeSubastas[index] = subastaActualizada
            return true
        }
        return false
    }
}