package com.example.loginapp

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * ViewModel to handle login/logout logic and state management.
 * It depends on LoginRepository for data access.
 */
class AuthViewModel(
    private val repository: LoginRepository // Dependency to be injected
) : ViewModel() {

    // Mutable state that the Composable UI will observe
    var authState by mutableStateOf(AuthState())
        private set

    fun login(username: String, passwordHash: String) {
        // Ignore login if already loading
        if (authState.isLoading) return

        authState = authState.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                // Call the Repository from the background thread (coroutine)
                val result = repository.attemptLogin(username, passwordHash)
                
                // Update the state based on the result
                authState = result.copy(isLoading = false)

            } catch (e: Exception) {
                // Handle unexpected errors (e.g., connection timeout)
                Log.e("AuthViewModel", "Login Exception", e)
                authState = AuthState(
                    userId = "Error",
                    errorMessage = "Login failed due to a network error.",
                    isLoading = false
                )
            }
        }
    }

    fun logout() {
        repository.clearSession()
        authState = AuthState(userId = "Logged out")
    }
}
