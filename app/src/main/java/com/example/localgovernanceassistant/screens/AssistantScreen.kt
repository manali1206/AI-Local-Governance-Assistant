package com.example.localgovernanceassistant.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
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
import com.example.localgovernanceassistant.ai.model.ChatMessage
import com.example.localgovernanceassistant.ai.repository.AIRepository
import kotlinx.coroutines.launch

@Composable
fun AssistantScreen(
    modifier: Modifier = Modifier
) {
    var question by remember {
        mutableStateOf("")
    }

    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                message = "Hello! I am your Local Governance Assistant.",
                isUser = false
            )
        )
    }

    val scope = rememberCoroutineScope()

    val aiRepository = remember {
        AIRepository()
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
                },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text("Type your question...")
                },
                singleLine = true
            )

            Button(
                onClick = {
                    if (question.isNotBlank()) {

                        val userQuestion = question

                        messages.add(
                            ChatMessage(
                                message = userQuestion,
                                isUser = true
                            )
                        )

                        question = ""

                        scope.launch {
                            val response =
                                aiRepository.sendMessage(userQuestion)

                            messages.add(
                                ChatMessage(
                                    message = response.message,
                                    isUser = false
                                )
                            )
                        }
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Send")
            }
        }
    }
}