package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val WireframeColorScheme = lightColorScheme(
    primary = PrimaryWireframe,
    onPrimary = OnPrimaryWireframe,
    secondary = SecondaryWireframe,
    onSecondary = OnSecondaryWireframe,
    background = WireframeBackground,
    onBackground = WireframeOnBackground,
    surface = WireframeBackground,
    onSurface = OnSurfaceWireframe,
    surfaceVariant = SurfaceContainerHighest,
    onSurfaceVariant = OnSurfaceVariantWireframe,
    outline = WireframeOutline,
    outlineVariant = WireframeOutlineVariant
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false, // Force consistent high-fidelity structural wireframe
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // Both states map to our rigorous structural wireframe styling scheme
    MaterialTheme(
        colorScheme = WireframeColorScheme,
        typography = Typography,
        content = content
    )
}
