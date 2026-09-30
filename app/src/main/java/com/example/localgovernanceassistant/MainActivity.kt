package com.example.localgovernanceassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.localgovernanceassistant.navigation.AppNavigation
import com.example.localgovernanceassistant.ui.theme.AILocalGovernanceAssistantTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            AILocalGovernanceAssistantTheme {
                AppNavigation()
            }
        }
    }
}