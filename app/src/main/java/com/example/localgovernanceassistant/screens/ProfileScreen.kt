package com.example.localgovernanceassistant.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localgovernanceassistant.supabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch


@Composable
fun ProfileScreen(
    onLogout: () -> Unit
) {

    val scope = rememberCoroutineScope()
    val currentUser = supabaseClient.auth.currentUserOrNull()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "My Profile",
            fontSize = 28.sp
        )

        Text(
            text = "Manage your account and preferences.",
            fontSize = 15.sp,
            modifier = Modifier.padding(
                top = 10.dp,
                bottom = 10.dp
            )
        )
        currentUser?.email?.let { email ->
            Text(
                text = email,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }

        Button(
            onClick = {
                scope.launch {
                    supabaseClient.auth.signOut()
                    onLogout()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
    }
}