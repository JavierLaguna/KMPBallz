package dev.jlaguna.kmpballz.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

// TODO: JLI - https://medium.com/@aleksandr.komolkin/styling-your-compose-multiplatform-app-a-guide-to-fonts-and-themes-f89187a48c77

@Composable
fun Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
//        typography = AppTypography(), // TODO: JLI - Add
//        shapes = Shapes, // TODO: JLI - Add
        content = content,
    )
}