package com.example.localgovernanceassistant.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onAssistantClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "AI Local Governance Assistant",
            fontSize = 24.sp
        )

        Text(
            text = "Your digital assistant for local government services.",
            modifier = Modifier.padding(top = 12.dp),
            fontSize = 16.sp
        )

        TextButton(
            onClick = onAssistantClick,
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text("Ask AI Assistant")
        }
    }
}