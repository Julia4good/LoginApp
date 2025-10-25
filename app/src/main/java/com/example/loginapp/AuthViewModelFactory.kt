package com.example.loginapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Custom Factory required to instantiate AuthViewModel because it has
 * constructor arguments (the LoginRepository dependency).
 */
class AuthViewModelFactory(
    private val repository: LoginRepository
) : ViewModelProvider.Factory {
    
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            // This is where the dependency (repository) is injected
            return AuthViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
