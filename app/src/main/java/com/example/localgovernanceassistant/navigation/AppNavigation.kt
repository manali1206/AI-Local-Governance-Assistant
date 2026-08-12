package com.example.localgovernanceassistant.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.localgovernanceassistant.screens.AssistantScreen
import com.example.localgovernanceassistant.screens.GrievanceScreen
import com.example.localgovernanceassistant.screens.HomeScreen
import com.example.localgovernanceassistant.screens.NearbyScreen
import com.example.localgovernanceassistant.screens.ProfileScreen
import com.example.localgovernanceassistant.screens.SchemesScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(
                onAssistantClick = {
                    navController.navigate("assistant")
                }
            )
        }

        composable("assistant") {
            AssistantScreen()
        }

        composable("schemes") {
            SchemesScreen()
        }

        composable("grievance") {
            GrievanceScreen()
        }

        composable("nearby") {
            NearbyScreen()
        }

        composable("profile") {
            ProfileScreen()
        }
    }
}