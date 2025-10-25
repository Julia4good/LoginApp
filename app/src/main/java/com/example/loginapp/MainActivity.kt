package com.example.loginapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier

// All necessary architectural components (AuthState, Repository, ViewModel, Factory, and AuthScreen)
// are in the same package and are implicitly imported.

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- View Model Setup with Factory (Dependency Injection) ---

        // 1. Initialize the required dependency (LoginRepository)
        // This simulates the actual connection/API handler for the MySQL backend.
        val loginRepository = LoginRepository()

        // 2. Create the custom factory, injecting the dependency
        // This step is crucial because AuthViewModel has constructor arguments (the repository).
        val viewModelFactory = AuthViewModelFactory(loginRepository)

        // 3. The Android system uses the factory to create the ViewModel instance.
        // The viewModels() delegate ensures the ViewModel survives configuration changes.
        val viewModel: AuthViewModel by viewModels { viewModelFactory }

        setContent {
            // Apply the main theme and set the root Composable
            MaterialTheme { 
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Call the AuthScreen, passing the initialized ViewModel
                    AuthScreen(viewModel)
                }
            }
        }
    }
}
