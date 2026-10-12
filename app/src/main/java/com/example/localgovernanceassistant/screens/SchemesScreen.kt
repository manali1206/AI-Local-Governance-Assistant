
package com.example.localgovernanceassistant.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class GovernmentScheme(
    val name: String,
    val description: String,
    val category: String
)

private val CivicBlue = Color(0xFF0D47A1)
private val SecondaryBlue = Color(0xFF1565C0)
private val LightBlue = Color(0xFFE3F2FD)
private val ScreenBackground = Color(0xFFF5F8FC)
private val MainText = Color(0xFF172B4D)
private val SecondaryText = Color(0xFF5F6B7A)
private val CardBorder = Color(0xFFE1E8F0)

@Composable
fun SchemesScreen() {
    val schemes = listOf(
        GovernmentScheme(
            "Government Schemes",
            "Explore government schemes and services available to citizens.",
            "General"
        ),
        GovernmentScheme(
            "Education Support",
            "Find scholarships and education-related government support.",
            "Education"
        ),
        GovernmentScheme(
            "Farmer Support",
            "Explore agricultural assistance and farmer welfare schemes.",
            "Agriculture"
        ),
        GovernmentScheme(
            "Housing Support",
            "Find information about government housing assistance.",
            "Housing"
        )
    )

    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = listOf(
        "All", "Education", "Agriculture", "Housing", "General"
    )

    val filteredSchemes = schemes.filter { scheme ->
        val matchesSearch =
            scheme.name.contains(searchText, ignoreCase = true) ||
                    scheme.description.contains(searchText, ignoreCase = true)

        val matchesCategory =
            selectedCategory == "All" ||
                    scheme.category == selectedCategory

        matchesSearch && matchesCategory
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackground),
        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = 20.dp,
            bottom = 28.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Surface(
                    shape = RoundedCornerShape(50.dp),
                    color = LightBlue
                ) {
                    Text(
                        text = "CITIZEN SERVICES",
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
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
                    text = "Government Schemes",
                    fontSize = 28.sp,
                    lineHeight = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainText
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Discover support and public welfare services for you.",
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    color = SecondaryText
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CivicBlue
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Find the right support",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Search schemes by name or explore categories below.",
                            fontSize = 13.sp,
                            lineHeight = 19.sp,
                            color = Color(0xFFE3F2FD)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .background(
                                Color.White.copy(alpha = 0.15f),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "₹",
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        item {
            Column {
                Text(
                    text = "Search schemes",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainText
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            "Search by name or keyword",
                            color = SecondaryText
                        )
                    },
                    leadingIcon = {
                        Text(
                            text = "⌕",
                            fontSize = 27.sp,
                            color = CivicBlue
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SecondaryBlue,
                        unfocusedBorderColor = CardBorder,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = MainText,
                        unfocusedTextColor = MainText,
                        cursorColor = CivicBlue
                    )
                )
            }
        }

        item {
            Column {
                Text(
                    text = "Browse categories",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainText
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.horizontalScroll(
                        rememberScrollState()
                    ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { category ->
                        FilterChip(
                            selected = selectedCategory == category,
                            onClick = { selectedCategory = category },
                            label = {
                                Text(
                                    text = category,
                                    fontWeight = FontWeight.Medium
                                )
                            },
                            shape = RoundedCornerShape(50.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = CivicBlue,
                                selectedLabelColor = Color.White,
                                containerColor = Color.White,
                                labelColor = SecondaryText
                            )
                        )
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Available schemes",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainText
                )

                Surface(
                    color = LightBlue,
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text(
                        text = "${filteredSchemes.size} found",
                        modifier = Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 6.dp
                        ),
                        color = CivicBlue,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        if (filteredSchemes.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "⌕",
                            fontSize = 38.sp,
                            color = CivicBlue
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "No schemes found",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MainText
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = "Try another keyword or select a different category.",
                            fontSize = 13.sp,
                            color = SecondaryText
                        )
                    }
                }
            }
        } else {
            items(filteredSchemes) { scheme ->
                SchemeCard(scheme = scheme)
            }
        }

        item {
            Text(
                text = "Please verify scheme details and eligibility on the relevant official government portal.",
                fontSize = 12.sp,
                lineHeight = 18.sp,
                color = SecondaryText,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
private fun SchemeCard(scheme: GovernmentScheme) {
    val categoryCode = when (scheme.category) {
        "Education" -> "ED"
        "Agriculture" -> "AG"
        "Housing" -> "HO"
        else -> "GS"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(LightBlue, RoundedCornerShape(15.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = categoryCode,
                    color = CivicBlue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Surface(
                    color = LightBlue,
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text(
                        text = scheme.category,
                        modifier = Modifier.padding(
                            horizontal = 9.dp,
                            vertical = 4.dp
                        ),
                        color = CivicBlue,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(9.dp))

                Text(
                    text = scheme.name,
                    fontSize = 17.sp,
                    lineHeight = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainText
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = scheme.description,
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    color = SecondaryText
                )
            }
        }
    }
}
