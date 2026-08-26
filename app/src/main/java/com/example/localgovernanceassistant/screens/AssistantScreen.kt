package com.example.localgovernanceassistant.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class AssistantMessage(
    val message: String,
    val isUser: Boolean
)

@Composable
fun AssistantScreen(
    modifier: Modifier = Modifier
) {
    var question by remember {
        mutableStateOf("")
    }

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

            IconButton(
                onClick = {
                    if (question.isNotBlank()) {

                        messages.add(
                            AssistantMessage(
                                message = question,
                                isUser = true
                            )
                        )
                        messages.add(
                            AssistantMessage(
                                message = "I received your question. AI response will be connected next.",
                                isUser = false
                            )
                        )

                        question = ""
                    }
                }
            ) {
                Text(
                    text = "Send",
                    fontSize = 14.sp
                )
            }
        }
    }
}