package com.example.localgovernanceassistant.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// ========================================
// DARK COLOUR SCHEME
// ========================================

private val DarkColorScheme = darkColorScheme(

    primary = GovernmentDarkPrimary,
    onPrimary = GovernmentDarkOnPrimary,

    secondary = GovernmentDarkPrimary,
    onSecondary = GovernmentDarkOnPrimary,

    tertiary = Color(0xFF90CAF9),
    onTertiary = Color(0xFF082B57),

    background = GovernmentDarkBackground,
    onBackground = GovernmentDarkText,

    surface = GovernmentDarkSurface,
    onSurface = GovernmentDarkText,

    surfaceVariant = Color(0xFF26384B),
    onSurfaceVariant = GovernmentDarkSecondaryText,

    outline = GovernmentDarkBorder,

    error = Color(0xFFEF9A9A),
    onError = Color(0xFF450A0A)
)


// ========================================
// LIGHT COLOUR SCHEME
// ========================================

private val LightColorScheme = lightColorScheme(

    primary = GovernmentBlue,
    onPrimary = Color.White,

    primaryContainer = GovernmentLightBlue,
    onPrimaryContainer = Color(0xFF082D62),

    secondary = GovernmentSecondaryBlue,
    onSecondary = Color.White,

    secondaryContainer = Color(0xFFD6E7FA),
    onSecondaryContainer = Color(0xFF12345A),

    tertiary = Color(0xFF1976D2),
    onTertiary = Color.White,

    background = GovernmentBackground,
    onBackground = GovernmentText,

    surface = GovernmentSurface,
    onSurface = GovernmentText,

    surfaceVariant = GovernmentSurfaceVariant,
    onSurfaceVariant = GovernmentSecondaryText,

    outline = GovernmentBorder,

    error = GovernmentError,
    onError = Color.White
)


// ========================================
// APPLICATION THEME
// ========================================

@Composable
fun AILocalGovernanceAssistantTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),

    // Keep our chosen blue palette by default.
    // Dynamic colours can be enabled explicitly.
    dynamicColor: Boolean = false,

    content: @Composable () -> Unit
) {

    val colorScheme = when {

        dynamicColor &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {

            val context = androidx.compose.ui.platform.LocalContext.current

            if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }

        darkTheme -> DarkColorScheme

        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}