package com.example.localgovernanceassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.localgovernanceassistant.navigation.AppNavigation
import com.example.localgovernanceassistant.ui.theme.AILocalGovernanceAssistantTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AILocalGovernanceAssistantTheme {
                AppNavigation()
            }
        }
    }
}