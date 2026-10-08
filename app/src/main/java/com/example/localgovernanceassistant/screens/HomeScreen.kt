package com.example.localgovernanceassistant.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localgovernanceassistant.R


data class ServiceItem(
    val title: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)


@Composable
fun HomeScreen(
    onAssistantClick: () -> Unit
) {

    var searchText by remember {
        mutableStateOf("")
    }

    // Localized strings

    val aiLocalGovernance =
        stringResource(R.string.ai_local_governance)

    val yourLocalGovernmentAssistant =
        stringResource(R.string.your_local_government_assistant)

    val searchGovernmentServices =
        stringResource(R.string.search_government_services)

    val search =
        stringResource(R.string.search)

    val aiAssistant =
        stringResource(R.string.ai_assistant)

    val askQuestionsGovernmentServices =
        stringResource(R.string.ask_questions_government_services)

    val governmentSchemes =
        stringResource(R.string.government_schemes)

    val findSchemesCheckEligibility =
        stringResource(R.string.find_schemes_check_eligibility)

    val grievances =
        stringResource(R.string.grievances)

    val submitTrackComplaints =
        stringResource(R.string.submit_track_complaints)

    val nearbyServices =
        stringResource(R.string.nearby_services)

    val findNearbyGovernmentOffices =
        stringResource(R.string.find_nearby_government_offices)

    val needHelp =
        stringResource(R.string.need_help)

    val askAiGovernmentServices =
        stringResource(R.string.ask_ai_government_services)

    val askAi =
        stringResource(R.string.ask_ai)

    val servicesTitle =
        stringResource(R.string.services)


    val services = listOf(

        ServiceItem(
            aiAssistant,
            askQuestionsGovernmentServices,
            Icons.Default.Chat
        ),

        ServiceItem(
            governmentSchemes,
            findSchemesCheckEligibility,
            Icons.AutoMirrored.Filled.Assignment
        ),

        ServiceItem(
            grievances,
            submitTrackComplaints,
            Icons.AutoMirrored.Filled.Assignment
        ),

        ServiceItem(
            nearbyServices,
            findNearbyGovernmentOffices,
            Icons.Default.LocationOn
        )
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        // App title

        Text(
            text = aiLocalGovernance,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = yourLocalGovernmentAssistant,
            fontSize = 15.sp,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // Search

        OutlinedTextField(
            value = searchText,

            onValueChange = {
                searchText = it
            },

            modifier = Modifier.fillMaxWidth(),

            placeholder = {
                Text(searchGovernmentServices)
            },

            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = search
                )
            },

            singleLine = true
        )


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // AI Assistant card

        Card(
            modifier = Modifier.fillMaxWidth(),

            colors = CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme.primaryContainer
            )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Chat,

                    contentDescription =
                        aiAssistant,

                    modifier =
                        Modifier.size(42.dp)
                )


                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 14.dp)
                ) {

                    Text(
                        text = needHelp,

                        fontSize = 18.sp,

                        fontWeight =
                            FontWeight.Bold
                    )

                    Text(
                        text =
                            askAiGovernmentServices,

                        fontSize = 13.sp
                    )
                }


                TextButton(
                    onClick = onAssistantClick
                ) {

                    Text(askAi)
                }
            }
        }


        Spacer(
            modifier = Modifier.height(24.dp)
        )


        // Services title

        Text(
            text = servicesTitle,

            fontSize = 21.sp,

            fontWeight =
                FontWeight.Bold
        )


        Spacer(
            modifier = Modifier.height(12.dp)
        )


        // Services grid

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),

            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(12.dp),

            verticalArrangement =
                Arrangement.spacedBy(12.dp)
        ) {

            items(services) { service ->

                Card(
                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier =
                            Modifier.padding(16.dp)
                    ) {

                        Icon(
                            imageVector =
                                service.icon,

                            contentDescription =
                                service.title,

                            modifier =
                                Modifier.size(32.dp)
                        )


                        Spacer(
                            modifier =
                                Modifier.height(12.dp)
                        )


                        Text(
                            text =
                                service.title,

                            fontWeight =
                                FontWeight.Bold,

                            fontSize = 16.sp
                        )


                        Spacer(
                            modifier =
                                Modifier.height(6.dp)
                        )


                        Text(
                            text =
                                service.description,

                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}