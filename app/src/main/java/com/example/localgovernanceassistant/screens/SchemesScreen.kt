package com.example.localgovernanceassistant.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class GovernmentScheme(
    val name: String,
    val category: String,
    val purpose: String,
    val benefits: String,
    val eligibility: String,
    val application: String
)

@Composable
fun SchemesScreen() {

    val schemes = listOf(

        GovernmentScheme(
            name = "PM-KISAN",
            category = "Agriculture",
            purpose = "Provides financial support to eligible farmer families.",
            benefits = "Eligible farmers receive financial assistance through the scheme.",
            eligibility = "Eligibility is based on the rules and conditions specified by the Government.",
            application = "Farmers can use the official PM-KISAN channels and local government assistance for registration and status information."
        ),

        GovernmentScheme(
            name = "Pradhan Mantri Awas Yojana",
            category = "Housing",
            purpose = "Supports eligible households in obtaining or improving housing.",
            benefits = "Housing assistance is provided according to the applicable scheme guidelines.",
            eligibility = "Eligibility depends on household and economic criteria specified under the relevant PMAY component.",
            application = "Applicants can check the applicable PMAY process through official government portals or local authorities."
        ),

        GovernmentScheme(
            name = "MGNREGA",
            category = "Employment",
            purpose = "Provides a legal framework for rural employment and livelihood security.",
            benefits = "Eligible rural households can seek employment according to the scheme's provisions.",
            eligibility = "Rural households whose adult members are willing to perform unskilled manual work may seek employment under the Act.",
            application = "Workers can register and request work through the local Gram Panchayat."
        ),

        GovernmentScheme(
            name = "Ayushman Bharat",
            category = "Healthcare",
            purpose = "Supports access to healthcare for eligible beneficiaries under its applicable components.",
            benefits = "Eligible beneficiaries can receive healthcare benefits according to the applicable program guidelines.",
            eligibility = "Eligibility depends on the applicable beneficiary database and government criteria.",
            application = "Eligibility and available services can be checked through official government channels and participating healthcare facilities."
        ),

        GovernmentScheme(
            name = "Swachh Bharat Mission",
            category = "Sanitation",
            purpose = "Promotes sanitation, cleanliness, and improved waste management.",
            benefits = "Supports sanitation and cleanliness activities in communities.",
            eligibility = "Benefits and activities depend on the applicable program and local implementation.",
            application = "Citizens can contact their local government body for information about available activities and support."
        ),

        GovernmentScheme(
            name = "Education Scholarships",
            category = "Education",
            purpose = "Provides financial assistance and scholarships to eligible students.",
            benefits = "Eligible students may receive financial support for education according to specific scholarship rules.",
            eligibility = "Eligibility varies by scholarship, educational level, income, category, and other conditions.",
            application = "Students should check the applicable official scholarship portal and submit the required documents."
        )
    )

    var searchText by remember {
        mutableStateOf("")
    }

    var selectedCategory by remember {
        mutableStateOf("All")
    }

    val categories = listOf(
        "All",
        "Education",
        "Agriculture",
        "Housing",
        "General"
    )

    val filteredSchemes = schemes.filter { scheme ->

        val matchesSearch =
            scheme.name.contains(searchText, ignoreCase = true) ||
                    scheme.purpose.contains(searchText, ignoreCase = true)

        val matchesCategory =
            selectedCategory == "All" ||
                    scheme.category == selectedCategory

        matchesSearch && matchesCategory
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
                bottom = 16.dp
            )
        )

        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Search schemes")
            },
            singleLine = true
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            categories.forEach { category ->

                FilterChip(
                    selected = selectedCategory == category,
                    onClick = {
                        selectedCategory = category
                    },
                    label = {
                        Text(category)
                    }
                )
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            items(filteredSchemes) { scheme ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors()
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