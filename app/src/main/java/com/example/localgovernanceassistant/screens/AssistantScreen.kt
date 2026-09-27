package com.example.localgovernanceassistant.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

data class AssistantMessage(
    val message: String,
    val isUser: Boolean
)

@Composable
fun AssistantScreen(
    modifier: Modifier = Modifier,
    onAskAi: (suspend (String) -> String)? = null
) {

    var question by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

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
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(4.dp)
                        )

                        Text(
                            text = "Assistant is thinking...",
                            modifier = Modifier.padding(
                                start = 10.dp,
                                top = 8.dp
                            )
                        )
                    }
                }
            }
        }

        if (errorMessage.isNotEmpty()) {

            Text(
                text = errorMessage,
                fontSize = 14.sp,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 6.dp
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            OutlinedTextField(
                value = question,
                onValueChange = {
                    question = it
                    errorMessage = ""
                },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text("Type your question...")
                },
                singleLine = true,
                enabled = !isLoading
            )

            IconButton(
                onClick = {

                    val userQuestion = question.trim()

                    if (userQuestion.isEmpty()) {
                        errorMessage = "Please enter a question."
                        return@IconButton
                    }

                    if (isLoading) {
                        return@IconButton
                    }

                    messages.add(
                        AssistantMessage(
                            message = userQuestion,
                            isUser = true
                        )
                    )

                    question = ""
                    errorMessage = ""

                    scope.launch {

                        isLoading = true

                        try {

                            if (onAskAi == null) {
                                throw IllegalStateException(
                                    "AI service is not connected yet."
                                )
                            }

                            val answer = onAskAi(userQuestion)

                            messages.add(
                                AssistantMessage(
                                    message = answer,
                                    isUser = false
                                )
                            )

                        } catch (e: Exception) {

                            errorMessage =
                                e.message ?: "Failed to get AI response."

                        } finally {

                            isLoading = false
                        }
                    }
                },
                enabled = !isLoading
            ) {
                Text(
                    text = "Send",
                    fontSize = 14.sp
                )
            }
        }
    }
}