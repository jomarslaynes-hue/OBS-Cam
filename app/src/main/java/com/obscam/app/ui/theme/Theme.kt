package com.obscam.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF7CCBFF),
    secondary = androidx.compose.ui.graphics.Color(0xFF9AE7C0),
    background = androidx.compose.ui.graphics.Color(0xFF0B1117),
    surface = androidx.compose.ui.graphics.Color(0xFF121A22),
    surfaceVariant = androidx.compose.ui.graphics.Color(0xFF1A2732)
)

private val LightColorScheme = lightColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF006EA8),
    secondary = androidx.compose.ui.graphics.Color(0xFF008B5E),
    background = androidx.compose.ui.graphics.Color(0xFFF4F8FB),
    surface = androidx.compose.ui.graphics.Color(0xFFFFFFFF),
    surfaceVariant = androidx.compose.ui.graphics.Color(0xFFEAEFF4)
)

@Composable
fun ObsCamTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
