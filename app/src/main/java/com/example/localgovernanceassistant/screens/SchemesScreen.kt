package com.example.localgovernanceassistant.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localgovernanceassistant.RetrofitClient
import com.example.localgovernanceassistant.Scheme
import kotlinx.coroutines.launch

@Composable
fun SchemesScreen() {

    var schemes by remember {
        mutableStateOf<List<Scheme>>(emptyList())
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {

        scope.launch {

            try {

                val response = RetrofitClient.apiService.getSchemes()

                if (response.isSuccessful) {

                    schemes = response.body()?.schemes ?: emptyList()

                    if (schemes.isEmpty()) {
                        errorMessage = "No government schemes available."
                    }

                } else {

                    errorMessage =
                        "Server error: ${response.code()}"

                }

            } catch (e: Exception) {

                errorMessage =
                    "Connection error: ${e.message ?: "Unable to connect to server."}"

            } finally {

                isLoading = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Government Schemes",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Explore government schemes, benefits and eligibility information.",
            fontSize = 15.sp,
            modifier = Modifier.padding(
                top = 6.dp,
                bottom = 20.dp
            )
        )

        if (isLoading) {

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }

        } else if (errorMessage != null) {

            Text(
                text = errorMessage!!,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.error
            )

        } else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                items(schemes) { scheme ->

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor =
                                MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(18.dp)
                        ) {

                            Text(
                                text = scheme.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = scheme.category,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(top = 5.dp)
                            )

                            Text(
                                text = "Purpose",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 12.dp)
                            )

                            Text(
                                text = scheme.purpose,
                                fontSize = 14.sp
                            )

                            Text(
                                text = "Benefits",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 10.dp)
                            )

                            Text(
                                text = scheme.benefits,
                                fontSize = 14.sp
                            )

                            Text(
                                text = "Eligibility",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 10.dp)
                            )

                            Text(
                                text = scheme.eligibility,
                                fontSize = 14.sp
                            )

                            Text(
                                text = "How to Apply",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 10.dp)
                            )

                            Text(
                                text = scheme.application,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}