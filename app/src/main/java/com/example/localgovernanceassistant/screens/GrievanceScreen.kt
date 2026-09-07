package com.example.localgovernanceassistant.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localgovernanceassistant.model.Grievance
import com.example.localgovernanceassistant.repository.GrievanceRepository
import com.example.localgovernanceassistant.supabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

@Composable
fun GrievanceScreen() {

    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    var message by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    var isRefreshing by remember { mutableStateOf(false) }

    var grievances by remember {
        mutableStateOf<List<Grievance>>(emptyList())
    }

    val scope = rememberCoroutineScope()

    val repository = remember {
        GrievanceRepository()
    }

    LaunchedEffect(Unit) {

        val currentUser = supabaseClient.auth.currentUserOrNull()

        if (currentUser != null) {

            try {
                grievances = repository.getUserGrievances(currentUser.id)
            } catch (e: Exception) {
                message = e.message ?: "Failed to load grievances."
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Grievances",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Submit and track your complaints.",
            fontSize = 15.sp,
            modifier = Modifier.padding(top = 6.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Submit a Grievance",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        message = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Grievance title")
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                        message = ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Describe your grievance")
                    },
                    minLines = 4
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {

                        if (title.isBlank() || description.isBlank()) {
                            message = "Please enter title and description."
                            return@Button
                        }

                        val currentUser =
                            supabaseClient.auth.currentUserOrNull()

                        if (currentUser == null) {
                            message = "Please login before submitting a grievance."
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

                                message =
                                    e.message ?: "Failed to submit grievance."

                            } finally {

                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                ) {
                    Text(
                        if (isLoading) {
                            "Submitting..."
                        } else {
                            "Submit Grievance"
                        }
                    )
                }

                if (message.isNotEmpty()) {

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = message)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "My Grievances",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Button(
            onClick = {
                val currentUser = supabaseClient.auth.currentUserOrNull()

                if (currentUser == null) {
                    message = "Please login to view your grievances."
                    return@Button
                }

                scope.launch {
                    isRefreshing = true

                    try {
                        grievances = repository.getUserGrievances(currentUser.id)
                        message = "Grievances refreshed."
                    } catch (e: Exception) {
                        message = e.message ?: "Failed to refresh grievances."
                    } finally {
                        isRefreshing = false
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isRefreshing && !isLoading
        ) {
            Text(
                if (isRefreshing) "Refreshing..." else "Refresh Status"
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Spacer(modifier = Modifier.height(8.dp))


        if (grievances.isEmpty()) {
            Text(
                text = "No grievances submitted yet.",
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }else {

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {

                items(grievances) { grievance ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Grievance ID: ${grievance.referenceId ?: "N/A"}",
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = grievance.title,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = grievance.description,
                                fontSize = 14.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Status: ${grievance.status}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )

                            if (grievance.createdAt != null) {
                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = "Submitted: ${grievance.createdAt}",
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}