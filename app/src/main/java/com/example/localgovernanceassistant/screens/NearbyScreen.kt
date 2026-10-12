
package com.example.localgovernanceassistant.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Park
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val NearbyBlue = Color(0xFF0D47A1)
private val NearbyLightBlue = Color(0xFFE3F2FD)
private val NearbyBackground = Color(0xFFF5F8FC)
private val NearbyText = Color(0xFF172B4D)
private val NearbySecondaryText = Color(0xFF5F6B7A)

@Composable
fun NearbyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NearbyBackground)
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Surface(
            color = NearbyLightBlue,
            shape = RoundedCornerShape(50.dp)
        ) {
            Text(
                text = "LOCAL SERVICES",
                modifier = Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 7.dp
                ),
                color = NearbyBlue,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Nearby Services",
            color = NearbyText,
            fontSize = 28.sp,
            lineHeight = 34.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = "Find government offices and public services around you.",
            color = NearbySecondaryText,
            fontSize = 14.sp,
            lineHeight = 21.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = NearbyBlue
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(86.dp)
                        .background(
                            Color.White.copy(alpha = 0.14f),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Services around you",
                    color = Color.White,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Discover nearby public offices and essential services from one place.",
                    color = Color(0xFFE3F2FD),
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        // Location and Google Maps functionality
                        // can be connected here later.
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = NearbyBlue
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.MyLocation,
                        contentDescription = null,
                        modifier = Modifier.size(19.dp)
                    )

                    Spacer(modifier = Modifier.size(9.dp))

                    Text(
                        text = "Find Nearby Services",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        Text(
            text = "Explore service types",
            color = NearbyText,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        NearbyServiceCard(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountBalance,
                    contentDescription = null,
                    tint = NearbyBlue,
                    modifier = Modifier.size(25.dp)
                )
            },
            title = "Government Offices",
            description = "Public administration and citizen service offices"
        )

        Spacer(modifier = Modifier.height(12.dp))

        NearbyServiceCard(
            icon = {
                Icon(
                    imageVector = Icons.Default.LocalHospital,
                    contentDescription = null,
                    tint = NearbyBlue,
                    modifier = Modifier.size(25.dp)
                )
            },
            title = "Health Services",
            description = "Hospitals and public healthcare facilities"
        )

        Spacer(modifier = Modifier.height(12.dp))

        NearbyServiceCard(
            icon = {
                Icon(
                    imageVector = Icons.Default.Park,
                    contentDescription = null,
                    tint = NearbyBlue,
                    modifier = Modifier.size(25.dp)
                )
            },
            title = "Public Facilities",
            description = "Explore useful public amenities in your area"
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Map,
                contentDescription = null,
                tint = NearbySecondaryText,
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = "Live location and map results are not connected yet.",
                color = NearbySecondaryText,
                fontSize = 12.sp,
                lineHeight = 17.sp
            )
        }
    }
}

@Composable
private fun NearbyServiceCard(
    icon: @Composable () -> Unit,
    title: String,
    description: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        NearbyLightBlue,
                        RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }

            Spacer(modifier = Modifier.size(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    color = NearbyText,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = description,
                    color = NearbySecondaryText,
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }
}