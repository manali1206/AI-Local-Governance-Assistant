
package com.example.localgovernanceassistant.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localgovernanceassistant.askGemini
import kotlinx.coroutines.launch

data class AssistantMessage(
    val message: String,
    val isUser: Boolean
)

@Composable
fun AssistantScreen(
    modifier: Modifier = Modifier
) {
    var question by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val messages = remember {
        mutableStateListOf(
            AssistantMessage(
                message = "Hello! I am your Local Governance Assistant.",
                isUser = false
            )
        )
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        Text(
            text = "AI Assistant",
            fontSize = 24.sp,
            modifier = Modifier.padding(20.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(messages) { message ->
                Text(
                    text = if (message.isUser) {
                        "You: ${message.message}"
                    } else {
                        "Assistant: ${message.message}"
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (isLoading) {
                item {
                    Text("Assistant: Thinking...")
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            OutlinedTextField(
                value = question,
                onValueChange = { question = it },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text("Type your question...")
                },
                singleLine = true,
                enabled = !isLoading
            )

            Button(
                onClick = {
                    val userQuestion = question.trim()

                    if (userQuestion.isNotEmpty() && !isLoading) {

                        messages.add(
                            AssistantMessage(
                                message = userQuestion,
                                isUser = true
                            )
                        )

                        question = ""
                        isLoading = true

                        scope.launch {
                            val answer = askGemini(userQuestion)

                            messages.add(
                                AssistantMessage(
                                    message = answer,
                                    isUser = false
                                )
                            )

                            isLoading = false
                        }
                    }
                },
                enabled = !isLoading,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Send")
            }
        }
    }
}