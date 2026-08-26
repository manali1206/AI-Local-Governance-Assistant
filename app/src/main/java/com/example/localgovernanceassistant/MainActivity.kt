package com.example.localgovernanceassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.localgovernanceassistant.screens.AssistantScreen
import com.example.localgovernanceassistant.ui.theme.AILocalGovernanceAssistantTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            AILocalGovernanceAssistantTheme {

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    AssistantScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}