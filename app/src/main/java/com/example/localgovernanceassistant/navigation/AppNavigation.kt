package com.example.localgovernanceassistant.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.localgovernanceassistant.screens.AssistantScreen
import com.example.localgovernanceassistant.screens.GrievanceScreen
import com.example.localgovernanceassistant.screens.HomeScreen
import com.example.localgovernanceassistant.screens.NearbyScreen
import com.example.localgovernanceassistant.screens.ProfileScreen
import com.example.localgovernanceassistant.screens.SchemesScreen

data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavItem(
            route = "home",
            title = "Home",
            icon = Icons.Default.Home
        ),
        BottomNavItem(
            route = "schemes",
            title = "Schemes",
            icon = Icons.AutoMirrored.Filled.Assignment
        ),
        BottomNavItem(
            route = "grievance",
            title = "Grievances",
            icon = Icons.Default.Report
        ),
        BottomNavItem(
            route = "nearby",
            title = "Nearby",
            icon = Icons.Default.LocationOn
        ),
        BottomNavItem(
            route = "profile",
            title = "Profile",
            icon = Icons.Default.Person
        )
    )

    Scaffold(

        bottomBar = {

            NavigationBar {

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                bottomNavItems.forEach { item ->

                    NavigationBarItem(
                        selected = currentRoute == item.route,

                        onClick = {
                            navController.navigate(item.route) {

                                popUpTo(
                                    navController.graph.findStartDestination().id
                                ) {
                                    saveState = true
                                }

                                launchSingleTop = true
                                restoreState = true
                            }
                        },

                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },

                        label = {
                            Text(item.title)
                        }
                    )
                }
            }
        }

    ) {innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
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
}