package com.example.proyectomoviles30.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyectomoviles30.data.repository.SubastaRepositoryImpl
import com.example.proyectomoviles30.data.repository.UserRepositoryImpl
import com.example.proyectomoviles30.presentation.auth.InicioDeSesionViewModel
import com.example.proyectomoviles30.presentation.auth.RegistroViewModel
import com.example.proyectomoviles30.presentation.main.CrearSubastaViewModel
import com.example.proyectomoviles30.presentation.main.DetalleSubastaViewModel
import com.example.proyectomoviles30.presentation.main.EditarPerfilViewModel
import com.example.proyectomoviles30.presentation.main.PerfilViewModel
import com.example.proyectomoviles30.presentation.main.SubastasViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    private val userRepository by lazy { UserRepositoryImpl(context) }
    private val subastaRepository by lazy { SubastaRepositoryImpl() }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InicioDeSesionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InicioDeSesionViewModel(userRepository) as T
        }
        if (modelClass.isAssignableFrom(RegistroViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegistroViewModel(userRepository) as T
        }
        if (modelClass.isAssignableFrom(SubastasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SubastasViewModel(subastaRepository) as T
        }
        if (modelClass.isAssignableFrom(CrearSubastaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CrearSubastaViewModel(subastaRepository) as T
        }
        if (modelClass.isAssignableFrom(EditarPerfilViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditarPerfilViewModel(userRepository) as T
        }
        if (modelClass.isAssignableFrom(PerfilViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PerfilViewModel(userRepository, subastaRepository) as T
        }
        if (modelClass.isAssignableFrom(DetalleSubastaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetalleSubastaViewModel(subastaRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}