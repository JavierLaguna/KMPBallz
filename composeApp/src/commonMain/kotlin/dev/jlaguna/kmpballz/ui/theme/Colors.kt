package dev.jlaguna.kmpballz.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val DragonBallOrange = Color(0xFFf0833a)
val WhitePrimary = Color(0xFFFCFAF7)
val BlackPrimary = Color(0xFF1C120D)
val SecondaryBackground = Color(0xFF2D2D2D)
val DarkOrangePrimary = Color(0xFF996B4D)
val DarkOrangeSecondary = Color(0xFF211712)

// Light Theme Colors
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
    onSecondary = LightTextColor,
    onPrimary = DarkTextColor,
    onPrimaryContainer = Color.Blue,
    onSecondaryContainer = SecondaryBackground,
    onTertiary = DarkOrangePrimary,
)

internal val DarkColorScheme = darkColorScheme(
    primary = DragonBallOrange,
    background = DarkOrangeSecondary,
    onBackground = DarkTextColor,
    surface = DarkOrangeSecondary,
    onSurface = DragonBallOrange,
    secondary = Color.Yellow,
    onSecondary = DarkTextColor,
    onPrimary = DarkTextColor,
    onPrimaryContainer = Color.Blue,
    onSecondaryContainer = SecondaryBackground,
    onTertiary = DarkOrangePrimary,
)