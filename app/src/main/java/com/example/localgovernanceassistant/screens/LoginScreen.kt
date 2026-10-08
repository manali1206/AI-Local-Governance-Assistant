
package com.example.localgovernanceassistant.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localgovernanceassistant.supabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch
import androidx.compose.ui.res.stringResource
import com.example.localgovernanceassistant.R

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val welcomeBack = stringResource(R.string.welcome_back)
    val loginToAssistant = stringResource(R.string.login_to_assistant)
    val emailLabel = stringResource(R.string.email)
    val passwordLabel = stringResource(R.string.password)
    val hidePassword = stringResource(R.string.hide_password)
    val showPassword = stringResource(R.string.show_password)
    val pleaseEnterEmailPassword = stringResource(R.string.please_enter_email_password)
    val loginFailed = stringResource(R.string.login_failed)
    val loggingIn = stringResource(R.string.logging_in)
    val createAccount = stringResource(R.string.create_account)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = welcomeBack,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
             text = loginToAssistant
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = emailText,
            onValueChange = {
                emailText = it
                errorMessage = ""
            },
            label = { Text(emailLabel) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = passwordText,
            onValueChange = {
                passwordText = it
                errorMessage = ""
            },
            label = { Text(passwordLabel) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        passwordVisible = !passwordVisible
                    }
                ) {
                    Icon(
                        imageVector = if (passwordVisible) {
                            Icons.Default.Visibility
                        } else {
                            Icons.Default.VisibilityOff
                        },
                        contentDescription = if (passwordVisible) {
                            "Hide password"
                        } else {
                            "Show password"
                        }
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        Button(
            onClick = {
                if (emailText.isBlank() || passwordText.isBlank()) {
                    errorMessage = pleaseEnterEmailPassword
                    return@Button
                }

                scope.launch {
                    isLoading = true

                    try {
                        supabaseClient.auth.signInWith(Email) {
                            email = emailText.trim()
                            password = passwordText
                        }

                        isLoading = false
                        onLoginSuccess()

                    } catch (e: Exception) {
                        isLoading = false
                        errorMessage = e.message ?: loginFailed
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text(
                text = if (isLoading) loggingIn else stringResource(R.string.login)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(createAccount)
        }
    }
}