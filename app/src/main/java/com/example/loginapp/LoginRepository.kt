package com.example.loginapp

import android.util.Log
import kotlinx.coroutines.delay

/**
 * Handles all data operations, simulating the backend call to the PHP script/MySQL.
 * This is the dependency required by the ViewModel.
 */
class LoginRepository {
    // Simulated credential for successful login
    private val VALID_USERNAME = "user"
    private val VALID_PASSWORD_HASH = "pass123" // Pretending this is a hash

    /**
     * Simulates making an HTTP request to the XAMPP server to fetch user data.
     * Must be called from a coroutine.
     */
    suspend fun attemptLogin(username: String, passwordHash: String): AuthState {
        // Simulate network/server latency (e.g., 2 seconds)
        delay(2000) 

        return if (username == VALID_USERNAME && passwordHash == VALID_PASSWORD_HASH) {
            Log.d("LoginRepo", "Login successful for $username")
            AuthState(
                isAuthenticated = true,
                userId = "user_12345",
                name = "Mr. Test User",
                email = "user@mysql.com",
                errorMessage = null
            )
        } else {
            Log.e("LoginRepo", "Login failed for $username")
            AuthState(
                isAuthenticated = false,
                userId = "Login Failed",
                errorMessage = "Invalid username or password.",
                name = "",
                email = ""
            )
        }
    }

    /**
     * Simulates clearing any local session data.
     */
    fun clearSession() {
        // In a real app, you might clear stored tokens or flags here.
        Log.d("LoginRepo", "Session cleared locally.")
    }
}
