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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localgovernanceassistant.R
import com.example.localgovernanceassistant.ai.model.ChatMessage
import com.example.localgovernanceassistant.ai.repository.AIRepository
import kotlinx.coroutines.launch

@Composable
fun AssistantScreen(
    modifier: Modifier = Modifier
) {

    val aiAssistant = stringResource(R.string.ai_assistant)
    val welcomeMessage = stringResource(R.string.ai_welcome_message)
    val youPrefix = stringResource(R.string.you_prefix)
    val assistantPrefix = stringResource(R.string.assistant_prefix)
    val assistantThinking = stringResource(R.string.assistant_thinking)
    val typeYourQuestion = stringResource(R.string.type_your_question)
    val pleaseEnterQuestion = stringResource(R.string.please_enter_question)
    val failedAiResponse = stringResource(R.string.failed_ai_response)
    val send = stringResource(R.string.send)

    var question by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                message = welcomeMessage,
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
            text = aiAssistant,
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
                        "$youPrefix: ${message.message}"
                    } else {
                        "$assistantPrefix: ${message.message}"
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
                            text = assistantThinking,
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
                    Text(typeYourQuestion)
                },
                singleLine = true,
                enabled = !isLoading
            )

            Button(
                onClick = {

                    val userQuestion = question.trim()

                    if (userQuestion.isEmpty()) {
                        errorMessage = pleaseEnterQuestion
                        return@Button
                    }

                    if (isLoading) return@Button

                    messages.add(
                        ChatMessage(
                            message = userQuestion,
                            isUser = true
                        )
                    )

                    question = ""
                    errorMessage = ""

                    scope.launch {

                        isLoading = true

                        try {

                            val response =
                                aiRepository.sendMessage(userQuestion)

                            messages.add(
                                ChatMessage(
                                    message = response.message,
                                    isUser = false
                                )
                            )

                        } catch (e: Exception) {

                            errorMessage =
                                e.message ?: failedAiResponse

                        } finally {

                            isLoading = false
                        }
                    }
                },
                modifier = Modifier.padding(start = 8.dp),
                enabled = !isLoading
            ) {
                Text(send)
            }
        }
    }
}