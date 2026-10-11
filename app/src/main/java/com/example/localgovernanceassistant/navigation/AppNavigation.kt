
package com.example.localgovernanceassistant.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.localgovernanceassistant.screens.AssistantScreen
import com.example.localgovernanceassistant.screens.GrievanceScreen
import com.example.localgovernanceassistant.screens.HomeScreen
import com.example.localgovernanceassistant.screens.LoginScreen
import com.example.localgovernanceassistant.screens.NearbyScreen
import com.example.localgovernanceassistant.screens.ProfileScreen
import com.example.localgovernanceassistant.screens.Registerscreen
import com.example.localgovernanceassistant.screens.SchemesScreen
import com.example.localgovernanceassistant.supabaseClient
import io.github.jan.supabase.auth.auth

data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    var startDestination by remember {
        mutableStateOf<String?>(null)
    }

    // Wait until Supabase has restored its saved session.
    LaunchedEffect(Unit) {
        supabaseClient.auth.awaitInitialization()

        startDestination =
            if (supabaseClient.auth.currentSessionOrNull() != null) {
                "home"
            } else {
                "login"
            }
    }

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

    val destination = startDestination

    if (destination == null) {
        // Keep the UI waiting while authentication initializes.
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {

        Scaffold(
            bottomBar = {

                val navBackStackEntry by
                    navController.currentBackStackEntryAsState()

                val currentRoute =
                    navBackStackEntry?.destination?.route

                if (
                    currentRoute != "login" &&
                    currentRoute != "register"
                ) {
                    NavigationBar {
                        bottomNavItems.forEach { item ->
                            NavigationBarItem(
                                selected = currentRoute == item.route,
                                onClick = {
                                    navController.navigate(item.route) {
                                        popUpTo(
                                            navController.graph
                                                .findStartDestination().id
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
            }
        ) { innerPadding ->

            NavHost(
                navController = navController,
                startDestination = destination,
                modifier = Modifier.padding(innerPadding)
            ) {

                composable("login") {
                    LoginScreen(
                        onLoginSuccess = {
                            navController.navigate("home") {
                                popUpTo("login") {
                                    inclusive = true
                                }
                            }
                        },
                        onRegisterClick = {
                            navController.navigate("register")
                        }
                    )
                }

                composable("register") {
                    Registerscreen(
                        onRegisterSuccess = {
                            navController.navigate("login") {
                                popUpTo("register") {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }

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
                    ProfileScreen(
                        onLogout = {
                            navController.navigate("login") {
                                popUpTo(0) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
