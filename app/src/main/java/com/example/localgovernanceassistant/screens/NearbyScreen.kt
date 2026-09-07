package com.example.localgovernanceassistant.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NearbyScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Location",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Nearby Services",
            fontSize = 26.sp
        )

        Text(
            text = "Find nearby government offices and public services.",
            fontSize = 15.sp,
            modifier = Modifier.padding(
                top = 10.dp,
                bottom = 20.dp
            )
        )

        Button(
            onClick = {
                // Google Maps/location functionality will be added later.
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Find Nearby Services")
        }
    }
}