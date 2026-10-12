
package com.example.localgovernanceassistant.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.example.localgovernanceassistant.api.RetrofitClient
import com.example.localgovernanceassistant.model.Grievance
import com.example.localgovernanceassistant.repository.GrievanceRepository
import com.example.localgovernanceassistant.supabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

private val CivicBlue = Color(0xFF0D47A1)
private val SecondaryBlue = Color(0xFF1565C0)
private val LightBlue = Color(0xFFE3F2FD)
private val ScreenBackground = Color(0xFFF5F8FC)
private val PrimaryText = Color(0xFF172B4D)
private val SecondaryText = Color(0xFF5F6B7A)
private val CardBorder = Color(0xFFE4EAF2)
private val SuccessGreen = Color(0xFF2E7D32)
private val WarningAmber = Color(0xFFED8B00)
private val ErrorRed = Color(0xFFC62828)

@Composable
fun GrievanceStatus(status: String) {
    val normalizedStatus = status.trim()

    val statusColor = when (normalizedStatus.lowercase()) {
        "pending" -> WarningAmber
        "in progress" -> SecondaryBlue
        "resolved" -> SuccessGreen
        "rejected" -> ErrorRed
        else -> SecondaryText
    }

    val statusBackground = when (normalizedStatus.lowercase()) {
        "pending" -> Color(0xFFFFF3E0)
        "in progress" -> LightBlue
        "resolved" -> Color(0xFFE8F5E9)
        "rejected" -> Color(0xFFFFEBEE)
        else -> Color(0xFFF0F2F5)
    }

    Surface(
        color = statusBackground,
        shape = RoundedCornerShape(50.dp)
    ) {
        Text(
            text = normalizedStatus.ifBlank { "Unknown" },
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 7.dp
            ),
            color = statusColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun GrievanceScreen() {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var isRefreshing by remember { mutableStateOf(false) }
    var grievances by remember {
        mutableStateOf<List<Grievance>>(emptyList())
    }

    val scope = rememberCoroutineScope()
    val repository = remember { GrievanceRepository() }

    LaunchedEffect(Unit) {
        try {
            val response = RetrofitClient.apiService.checkHealth()
            message = "Backend: ${response.status}"
        } catch (e: Exception) {
            message = "Backend connection failed: ${e.message}"
        }

        val currentUser = supabaseClient.auth.currentUserOrNull()

        if (currentUser != null) {
            try {
                grievances = repository.getUserGrievances(currentUser.id)
            } catch (e: Exception) {
                message = e.message ?: "Failed to load grievances."
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackground),
        contentPadding = PaddingValues(
            horizontal = 20.dp,
            vertical = 24.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Surface(
                    color = LightBlue,
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text(
                        text = "CITIZEN SERVICES",
                        modifier = Modifier.padding(
                            horizontal = 13.dp,
                            vertical = 7.dp
                        ),
                        color = CivicBlue,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Grievances",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryText
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Submit a complaint and keep track of its progress.",
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    color = SecondaryText
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CivicBlue
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 0.dp
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Your voice matters",
                            color = Color.White,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Report a civic issue and check updates on your submitted complaints.",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 13.sp,
                            lineHeight = 19.sp
                        )
                    }

                    Spacer(modifier = Modifier.size(12.dp))

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                Color.White.copy(alpha = 0.15f),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "!",
                            color = Color.White,
                            fontSize = 27.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    CardBorder
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 0.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Text(
                        text = "Submit a grievance",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryText
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Provide a short title and explain the issue clearly.",
                        fontSize = 13.sp,
                        lineHeight = 19.sp,
                        color = SecondaryText
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "Grievance title",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryText
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    OutlinedTextField(
                        value = title,
                        onValueChange = {
                            title = it
                            message = ""
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("e.g. Road repair required")
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SecondaryBlue,
                            unfocusedBorderColor = CardBorder,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            cursorColor = CivicBlue
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Description",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryText
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    OutlinedTextField(
                        value = description,
                        onValueChange = {
                            description = it
                            message = ""
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text("Describe the issue and its location...")
                        },
                        minLines = 4,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = SecondaryBlue,
                            unfocusedBorderColor = CardBorder,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            cursorColor = CivicBlue
                        )
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = {
                            if (title.isBlank() || description.isBlank()) {
                                message = "Please enter title and description."
                                return@Button
                            }

                            val currentUser =
                                supabaseClient.auth.currentUserOrNull()

                            if (currentUser == null) {
                                message =
                                    "Please login before submitting a grievance."
                                return@Button
                            }

                            scope.launch {
                                isLoading = true
                                message = ""

                                try {
                                    val grievance = Grievance(
                                        userId = currentUser.id,
                                        title = title.trim(),
                                        description = description.trim()
                                    )

                                    repository.submitGrievance(grievance)

                                    title = ""
                                    description = ""

                                    grievances =
                                        repository.getUserGrievances(currentUser.id)

                                    message = "Grievance submitted successfully."
                                } catch (e: Exception) {
                                    message = e.message
                                        ?: "Failed to submit grievance."
                                } finally {
                                    isLoading = false
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        enabled = !isLoading,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CivicBlue
                        )
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Text("Submitting...")
                        } else {
                            Text(
                                text = "Submit grievance",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    if (message.isNotBlank()) {
                        Spacer(modifier = Modifier.height(12.dp))

                        Surface(
                            color = if (
                                message.contains("successfully", ignoreCase = true)
                            ) {
                                Color(0xFFE8F5E9)
                            } else {
                                LightBlue
                            },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = message,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                color = PrimaryText,
                                fontSize = 13.sp,
                                lineHeight = 19.sp
                            )
                        }
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "My grievances",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryText
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "View your submitted complaints and their status.",
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        color = SecondaryText
                    )
                }

                Spacer(modifier = Modifier.size(8.dp))

                Surface(
                    color = LightBlue,
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text(
                        text = "${grievances.size}",
                        modifier = Modifier.padding(
                            horizontal = 13.dp,
                            vertical = 8.dp
                        ),
                        color = CivicBlue,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        item {
            Button(
                onClick = {
                    val currentUser =
                        supabaseClient.auth.currentUserOrNull()

                    if (currentUser == null) {
                        message = "Please login to view your grievances."
                        return@Button
                    }

                    scope.launch {
                        isRefreshing = true

                        try {
                            grievances =
                                repository.getUserGrievances(currentUser.id)
                            message = "Grievances refreshed."
                        } catch (e: Exception) {
                            message = e.message
                                ?: "Failed to refresh grievances."
                        } finally {
                            isRefreshing = false
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = !isRefreshing && !isLoading,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondaryBlue
                )
            ) {
                if (isRefreshing) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text("Refreshing...")
                } else {
                    Text(
                        text = "Refresh status",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        if (grievances.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        CardBorder
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(26.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .background(LightBlue, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "—",
                                color = CivicBlue,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "No grievances yet",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryText
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Your submitted complaints will appear here.",
                            fontSize = 13.sp,
                            lineHeight = 19.sp,
                            color = SecondaryText
                        )
                    }
                }
            }
        } else {
            items(
                items = grievances,
                key = { grievance ->
                    grievance.referenceId
                        ?: "${grievance.title}-${grievance.createdAt}"
                }
            ) { grievance ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        CardBorder
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 0.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "GRIEVANCE REFERENCE",
                                    fontSize = 10.sp,
                                    letterSpacing = 0.8.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SecondaryText
                                )

                                Spacer(modifier = Modifier.height(5.dp))

                                Text(
                                    text = grievance.referenceId ?: "N/A",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = CivicBlue
                                )
                            }

                            Spacer(modifier = Modifier.size(8.dp))

                            GrievanceStatus(status = grievance.status)
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = grievance.title,
                            fontSize = 17.sp,
                            lineHeight = 23.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryText
                        )

                        Spacer(modifier = Modifier.height(7.dp))

                        Text(
                            text = grievance.description,
                            fontSize = 14.sp,
                            lineHeight = 21.sp,
                            color = SecondaryText
                        )

                        if (grievance.createdAt != null) {
                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "Submitted: ${grievance.createdAt}",
                                fontSize = 11.sp,
                                color = SecondaryText
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}