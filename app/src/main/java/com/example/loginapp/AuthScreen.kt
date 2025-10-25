package com.example.loginapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

// --- UI (VIEW) LAYER ---

/**
 * Main Composable function that observes the ViewModel state and delegates rendering.
 */
@Composable
fun AuthScreen(viewModel: AuthViewModel) {
    // Observe the state from the ViewModel
    val state = viewModel.authState
    
    // Internal state for input fields (managed locally by the Composable)
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        
        Card(
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // RENDER BASED ON AUTHENTICATION STATE
                if (state.isAuthenticated) {
                    LoggedInContent(state, viewModel)
                } else {
                    LoginContent(
                        state = state,
                        username = username,
                        onUsernameChange = { username = it },
                        password = password,
                        onPasswordChange = { password = it },
                        // Action: call ViewModel function when button is clicked
                        onLoginClick = { viewModel.login(username, password) }
                    )
                }
            }
        }

        // RENDER LOADING OVERLAY
        if (state.isLoading) {
            LoadingOverlay()
        }
    }
}


@Composable
fun LoginContent(
    state: AuthState,
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Text("MySQL Login System", style = MaterialTheme.typography.headlineMedium)
    Spacer(Modifier.height(32.dp))

    // Username Field
    OutlinedTextField(
        value = username,
        onValueChange = onUsernameChange,
        label = { Text("Username (Try: user)") },
        leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Username") },
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(Modifier.height(16.dp))

    // Password Field
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Password (Try: pass123)") },
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(Modifier.height(24.dp))

    // Login Button
    Button(
        onClick = onLoginClick,
        enabled = !state.isLoading,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Login")
    }
    
    // Error Message
    state.errorMessage?.let { message ->
        Spacer(Modifier.height(16.dp))
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun LoggedInContent(state: AuthState, viewModel: AuthViewModel) {
    Text("Authentication Success!", style = MaterialTheme.typography.headlineSmall)
    Spacer(Modifier.height(24.dp))
    Text("Welcome, ${state.name}", style = MaterialTheme.typography.titleLarge)
    Spacer(Modifier.height(8.dp))
    Text("User ID: ${state.userId}")
    Spacer(Modifier.height(8.dp))
    Text("Email: ${state.email}")
    Spacer(Modifier.height(32.dp))
    
    Button(
        onClick = { viewModel.logout() },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Logout")
    }
}

@Composable
fun LoadingOverlay() {
    // Semi-transparent dark overlay to hint that the screen is blocked
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(64.dp)
        )
    }
}
