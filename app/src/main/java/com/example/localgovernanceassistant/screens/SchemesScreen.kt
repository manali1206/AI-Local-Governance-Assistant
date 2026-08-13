package com.example.localgovernanceassistant.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class GovernmentScheme(
    val name: String,
    val description: String,
    val category: String
)

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
            text = "Find schemes and check your eligibility.",
            fontSize = 15.sp,
            modifier = Modifier.padding(top = 6.dp, bottom = 20.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(schemes) { scheme ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {

                        Text(
                            text = scheme.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = scheme.category,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Text(
                            text = scheme.description,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}