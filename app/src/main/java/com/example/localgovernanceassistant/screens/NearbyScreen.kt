package com.example.localgovernanceassistant.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NearbyScreen() {

    val context = LocalContext.current

    fun openMaps(search: String) {
        val uri = Uri.parse(
            "geo:0,0?q=${Uri.encode(search)}"
        )

        val intent = Intent(
            Intent.ACTION_VIEW,
            uri
        )

        intent.setPackage("com.google.android.apps.maps")

        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            val browserUri = Uri.parse(
                "https://www.google.com/maps/search/?api=1&query=${Uri.encode(search)}"
            )

            context.startActivity(
                Intent(Intent.ACTION_VIEW, browserUri)
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Location",
            modifier = Modifier.size(60.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Nearby Services",
            fontSize = 26.sp
        )

        Text(
            text = "Find nearby government offices and public services.",
            fontSize = 15.sp,
            modifier = Modifier.padding(
                top = 10.dp,
                bottom = 25.dp
            )
        )

        Button(
            onClick = {
                openMaps("Government offices near me")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Government Offices")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                openMaps("Government hospitals near me")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Government Hospitals")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                openMaps("Government schools near me")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Government Schools")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                openMaps("Police stations near me")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Police Stations")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                openMaps("Gram Panchayat office near me")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Gram Panchayat Offices")
        }
    }
}