package com.ardondev.bc_pokedex.presentation.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat

private val DarkColorScheme = darkColorScheme(
    primary = blue,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = blue,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = surface
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun BCPokedexTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val window = (context as? Activity)?.window
    val view = LocalView.current

    if (!view.isInEditMode) {
        window?.let {
            val windowInsetsController = WindowInsetsControllerCompat(window, view)
            windowInsetsController.isAppearanceLightStatusBars = !darkTheme
        }
    }

    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}