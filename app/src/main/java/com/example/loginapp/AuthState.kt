package com.example.loginapp

/**
 * Data class representing the current authentication state.
 * This is the single source of truth observed by the UI.
 */
data class AuthState(
    val isAuthenticated: Boolean = false,
    val userId: String = "Not logged in",
    val name: String = "",
    val email: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
