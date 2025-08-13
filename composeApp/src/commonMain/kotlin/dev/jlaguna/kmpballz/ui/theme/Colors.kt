package dev.jlaguna.kmpballz.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val DragonBallOrange = Color(0xFFf0833a)
val WhitePrimary = Color(0xFFFCFAF7)
val BlackPrimary = Color(0xFF1C120D)

// Light Theme Colors
val DarkOrangePrimary = Color(0xFF211712)
val LightTextColor = BlackPrimary

// Dark Theme Colors
val DarkTextColor = WhitePrimary

internal val LightColorScheme = lightColorScheme(
    primary = DragonBallOrange,
    background = WhitePrimary,
    onBackground = LightTextColor,
    surface = WhitePrimary,
    onSurface = LightTextColor,
    secondary = Color.Green,
    onSecondary = LightTextColor
)

internal val DarkColorScheme = darkColorScheme(
    primary = DragonBallOrange,
    background = DarkOrangePrimary,
    onBackground = DarkTextColor,
    surface = DarkOrangePrimary,
    onSurface = DragonBallOrange,
    secondary = Color.Yellow,
    onSecondary = DarkTextColor
)