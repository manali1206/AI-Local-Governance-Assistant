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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localgovernanceassistant.R

data class GovernmentScheme(
    val name: String,
    val categoryKey: String,
    val category: String,
    val purpose: String,
    val benefits: String,
    val eligibility: String,
    val application: String,
    val officialUrl: String
)

@Composable
fun SchemesScreen() {

    val uriHandler = LocalUriHandler.current

    // LOCALIZED UI TEXT

    val governmentSchemesTitle =
        stringResource(R.string.government_schemes_title)

    val exploreSchemes =
        stringResource(R.string.explore_schemes)

    val searchSchemes =
        stringResource(R.string.search_schemes)

    val purpose =
        stringResource(R.string.purpose)

    val benefits =
        stringResource(R.string.benefits)

    val eligibility =
        stringResource(R.string.eligibility)

    val howToApply =
        stringResource(R.string.how_to_apply)

    val visitOfficialWebsite =
        stringResource(R.string.visit_official_website)

    // LOCALIZED CATEGORY NAMES

    val allCategory =
        stringResource(R.string.all)

    val educationCategory =
        stringResource(R.string.education)

    val agricultureCategory =
        stringResource(R.string.agriculture)

    val housingCategory =
        stringResource(R.string.housing)

    val employmentCategory =
        stringResource(R.string.employment)

    val healthcareCategory =
        stringResource(R.string.healthcare)

    val sanitationCategory =
        stringResource(R.string.sanitation)

    // GOVERNMENT SCHEMES

    val schemes = listOf(

        GovernmentScheme(
            name = stringResource(R.string.scheme_pm_kisan_name),
            categoryKey = "Agriculture",
            category = agricultureCategory,
            purpose = stringResource(R.string.scheme_pm_kisan_purpose),
            benefits = stringResource(R.string.scheme_pm_kisan_benefits),
            eligibility = stringResource(R.string.scheme_pm_kisan_eligibility),
            application = stringResource(R.string.scheme_pm_kisan_application),
            officialUrl = "https://pmkisan.gov.in/"
        ),

        GovernmentScheme(
            name = stringResource(R.string.scheme_pmay_name),
            categoryKey = "Housing",
            category = housingCategory,
            purpose = stringResource(R.string.scheme_pmay_purpose),
            benefits = stringResource(R.string.scheme_pmay_benefits),
            eligibility = stringResource(R.string.scheme_pmay_eligibility),
            application = stringResource(R.string.scheme_pmay_application),
            officialUrl = "https://pmay-urban.gov.in/"
        ),

        GovernmentScheme(
            name = stringResource(R.string.scheme_mgnrega_name),
            categoryKey = "Employment",
            category = employmentCategory,
            purpose = stringResource(R.string.scheme_mgnrega_purpose),
            benefits = stringResource(R.string.scheme_mgnrega_benefits),
            eligibility = stringResource(R.string.scheme_mgnrega_eligibility),
            application = stringResource(R.string.scheme_mgnrega_application),
            officialUrl = "https://nrega.dord.gov.in/"
        ),

        GovernmentScheme(
            name = stringResource(R.string.scheme_ayushman_name),
            categoryKey = "Healthcare",
            category = healthcareCategory,
            purpose = stringResource(R.string.scheme_ayushman_purpose),
            benefits = stringResource(R.string.scheme_ayushman_benefits),
            eligibility = stringResource(R.string.scheme_ayushman_eligibility),
            application = stringResource(R.string.scheme_ayushman_application),
            officialUrl = "https://pmjay.gov.in/"
        ),

        GovernmentScheme(
            name = stringResource(R.string.scheme_swachh_bharat_name),
            categoryKey = "Sanitation",
            category = sanitationCategory,
            purpose = stringResource(R.string.scheme_swachh_bharat_purpose),
            benefits = stringResource(R.string.scheme_swachh_bharat_benefits),
            eligibility = stringResource(R.string.scheme_swachh_bharat_eligibility),
            application = stringResource(R.string.scheme_swachh_bharat_application),
            officialUrl = "https://swachhbharatmission.ddws.gov.in/"
        ),

        GovernmentScheme(
            name = stringResource(R.string.scheme_education_scholarships_name),
            categoryKey = "Education",
            category = educationCategory,
            purpose = stringResource(R.string.scheme_education_scholarships_purpose),
            benefits = stringResource(R.string.scheme_education_scholarships_benefits),
            eligibility = stringResource(R.string.scheme_education_scholarships_eligibility),
            application = stringResource(R.string.scheme_education_scholarships_application),
            officialUrl = "https://scholarships.gov.in/"
        )
    )

    // CATEGORY FILTERS

    data class CategoryOption(
        val key: String,
        val label: String
    )

    val categories = listOf(
        CategoryOption("All", allCategory),
        CategoryOption("Education", educationCategory),
        CategoryOption("Agriculture", agricultureCategory),
        CategoryOption("Housing", housingCategory),
        CategoryOption("Employment", employmentCategory),
        CategoryOption("Healthcare", healthcareCategory),
        CategoryOption("Sanitation", sanitationCategory)
    )

    var searchText by remember {
        mutableStateOf("")
    }

    var selectedCategory by remember {
        mutableStateOf("All")
    }

    // FILTER SCHEMES

    val filteredSchemes = schemes.filter { scheme ->

        val matchesSearch =
            scheme.name.contains(searchText, ignoreCase = true) ||
                    scheme.purpose.contains(searchText, ignoreCase = true) ||
                    scheme.benefits.contains(searchText, ignoreCase = true) ||
                    scheme.eligibility.contains(searchText, ignoreCase = true)

        val matchesCategory =
            selectedCategory == "All" ||
                    scheme.categoryKey == selectedCategory

        matchesSearch && matchesCategory
    }

    // SCREEN

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = governmentSchemesTitle,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = exploreSchemes,
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
                Text(searchSchemes)
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
                    selected = selectedCategory == category.key,
                    onClick = {
                        selectedCategory = category.key
                    },
                    label = {
                        Text(category.label)
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
                            text = purpose,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 12.dp)
                        )

                        Text(
                            text = scheme.purpose,
                            fontSize = 14.sp
                        )

                        Text(
                            text = benefits,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 10.dp)
                        )

                        Text(
                            text = scheme.benefits,
                            fontSize = 14.sp
                        )

                        Text(
                            text = eligibility,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 10.dp)
                        )

                        Text(
                            text = scheme.eligibility,
                            fontSize = 14.sp
                        )

                        Text(
                            text = howToApply,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 10.dp)
                        )

                        Text(
                            text = scheme.application,
                            fontSize = 14.sp
                        )

                        // OFFICIAL WEBSITE BUTTON

                        TextButton(
                            onClick = {
                                uriHandler.openUri(scheme.officialUrl)
                            },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text(visitOfficialWebsite)
                        }
                    }
                }
            }
        }
    }
}