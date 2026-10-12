
package com.example.localgovernanceassistant.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localgovernanceassistant.ai.model.ChatMessage
import com.example.localgovernanceassistant.ai.repository.AIRepository
import kotlinx.coroutines.launch

private val AssistantBlue = Color(0xFF0D47A1)
private val AssistantLightBlue = Color(0xFFE3F2FD)
private val AssistantBackground = Color(0xFFF5F8FC)
private val AssistantText = Color(0xFF172B4D)
private val AssistantSecondaryText = Color(0xFF5F6B7A)
private val AssistantError = Color(0xFFC62828)

@Composable
fun AssistantScreen(
    modifier: Modifier = Modifier
) {
    var question by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                message = "Hello! I am your Local Governance Assistant. Ask me about civic services, government schemes, or local governance.",
                isUser = false
            )
        )
    }

    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val aiRepository = remember {
        AIRepository()
    }

    LaunchedEffect(messages.size, isLoading) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(
                index = messages.size - 1 +
                        if (isLoading) 1 else 0
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AssistantBackground)
            .imePadding()
    ) {
        // Header
        Surface(
            color = AssistantBlue,
            shadowElevation = 2.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = Color.White.copy(alpha = 0.15f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "AI",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = "AI Assistant",
                        color = Color.White,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = "Local Governance Support",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 13.sp
                    )
                }
            }
        }

        // Conversation area
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                horizontal = 16.dp,
                vertical = 20.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "YOUR CONVERSATION",
                    color = AssistantSecondaryText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }

            items(messages) { message ->
                ChatBubble(
                    message = message.message,
                    isUser = message.isUser
                )
            }

            if (isLoading) {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 4.dp)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = AssistantBlue,
                            strokeWidth = 2.dp
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "Preparing your response...",
                            color = AssistantSecondaryText,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            if (errorMessage.isNotEmpty()) {
                item {
                    Surface(
                        color = Color(0xFFFFEBEE),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = errorMessage,
                            color = AssistantError,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }

        // Message composer
        Surface(
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Ask your question",
                    color = AssistantText,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = question,
                        onValueChange = {
                            question = it
                            errorMessage = ""
                        },
                        modifier = Modifier.weight(1f),
                        placeholder = {
                            Text(
                                text = "Type a message...",
                                fontSize = 14.sp
                            )
                        },
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        enabled = !isLoading,
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = AssistantBlue,
                            unfocusedIndicatorColor = Color(0xFFD5DDE8),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            cursorColor = AssistantBlue
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            val userQuestion = question.trim()

                            if (userQuestion.isEmpty()) {
                                errorMessage =
                                    "Please enter a question before sending."
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
                            isLoading = true

                            scope.launch {
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
                                    errorMessage = e.message
                                        ?: "Unable to get a response. Please try again."
                                } finally {
                                    isLoading = false
                                }
                            }
                        },
                        modifier = Modifier.height(52.dp),
                        enabled = !isLoading,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AssistantBlue,
                            disabledContainerColor = AssistantBlue.copy(
                                alpha = 0.5f
                            )
                        )
                    ) {
                        Text(
                            text = if (isLoading) "..." else "Send",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Information is provided for assistance.",
                    color = AssistantSecondaryText,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
private fun ChatBubble(
    message: String,
    isUser: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) {
            Arrangement.End
        } else {
            Arrangement.Start
        },
        verticalAlignment = Alignment.Top
    ) {
        if (!isUser) {
            Box(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .size(32.dp)
                    .background(
                        color = AssistantBlue,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "AI",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            modifier = Modifier.fillMaxWidth(0.84f),
            horizontalAlignment = if (isUser) {
                Alignment.End
            } else {
                Alignment.Start
            }
        ) {
            Text(
                text = if (isUser) "You" else "Assistant",
                color = AssistantSecondaryText,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(
                    horizontal = 4.dp,
                    vertical = 4.dp
                )
            )

            Surface(
                color = if (isUser) AssistantBlue else Color.White,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (isUser) 16.dp else 4.dp,
                    bottomEnd = if (isUser) 4.dp else 16.dp
                ),
                shadowElevation = if (isUser) 0.dp else 1.dp
            ) {
                Text(
                    text = message,
                    color = if (isUser) Color.White else AssistantText,
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    modifier = Modifier.padding(
                        horizontal = 14.dp,
                        vertical = 12.dp
                    )
                )
            }
        }

        if (isUser) {
            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .size(32.dp)
                    .background(
                        color = AssistantLightBlue,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "U",
                    color = AssistantBlue,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
