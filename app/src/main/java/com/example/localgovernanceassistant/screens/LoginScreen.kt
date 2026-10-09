
package com.example.localgovernanceassistant.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localgovernanceassistant.R
import com.example.localgovernanceassistant.supabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class LoginProfile(
    val phone_number: String? = null
)

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {
    var emailText by remember { mutableStateOf("") }
    var phoneText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val welcomeBack = stringResource(R.string.welcome_back)
    val loginToAssistant = stringResource(R.string.login_to_assistant)
    val emailLabel = stringResource(R.string.email)
    val phoneLabel = stringResource(R.string.phone_number)
    val passwordLabel = stringResource(R.string.password)
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

        Text(text = loginToAssistant)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = emailText,
            onValueChange = {
                emailText = it
                errorMessage = ""
            },
            label = { Text(emailLabel) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = phoneText,
            onValueChange = { input ->
                phoneText = input.filter { it.isDigit() }.take(10)
                errorMessage = ""
            },
            label = { Text(phoneLabel) },
            prefix = { Text("+91 ") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            )
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
                            Icons.Default.VisibilityOff
                        } else {
                            Icons.Default.Visibility
                        },
                        contentDescription = if (passwordVisible) {
                            "Hide password"
                        } else {
                            "Show password"
                        }
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        Button(
            onClick = {
                errorMessage = ""

                when {
                    emailText.isBlank() || passwordText.isBlank() -> {
                        errorMessage =
                            "Please enter your email and password."
                    }

                    phoneText.isNotEmpty() && phoneText.length != 10 -> {
                        errorMessage =
                            "Enter a valid 10-digit phone number."
                    }

                    else -> {
                        scope.launch {
                            isLoading = true

                            try {
                                supabaseClient.auth.signInWith(Email) {
                                    email = emailText.trim()
                                    password = passwordText
                                }

                                val user =
                                    supabaseClient.auth.currentUserOrNull()
                                        ?: error(
                                            "Unable to identify your account."
                                        )

                                val profile = supabaseClient
                                    .from("profiles")
                                    .select(
                                        columns = Columns.list("phone_number")
                                    ) {
                                        filter {
                                            eq("id", user.id)
                                        }
                                    }
                                    .decodeSingle<LoginProfile>()

                                val savedPhone = profile.phone_number
                                    ?.filter { it.isDigit() }
                                    ?.takeLast(10)

                                when {
                                    savedPhone.isNullOrBlank() -> {
                                        onLoginSuccess()
                                    }

                                    phoneText.isBlank() -> {
                                        supabaseClient.auth.signOut()
                                        errorMessage =
                                            "Enter the phone number registered to this account."
                                    }

                                    savedPhone != phoneText -> {
                                        supabaseClient.auth.signOut()
                                        errorMessage =
                                            "Phone number does not match this account."
                                    }

                                    else -> {
                                        onLoginSuccess()
                                    }
                                }
                            } catch (e: Exception) {
                                try {
                                    supabaseClient.auth.signOut()
                                } catch (_: Exception) {
                                    // Ignore sign-out cleanup errors
                                }

                                if (errorMessage.isBlank()) {
                                    errorMessage =
                                        e.message ?: loginFailed
                                }
                            } finally {
                                isLoading = false
                            }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text(
                text = if (isLoading) {
                    loggingIn
                } else {
                    stringResource(R.string.login)
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text(text = createAccount)
        }
    }
}
