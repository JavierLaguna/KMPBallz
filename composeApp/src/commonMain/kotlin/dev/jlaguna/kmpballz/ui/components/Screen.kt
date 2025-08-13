package dev.jlaguna.kmpballz.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Screen(content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        content = content
    )
}