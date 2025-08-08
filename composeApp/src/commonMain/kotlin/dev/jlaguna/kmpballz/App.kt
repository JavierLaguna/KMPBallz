package dev.jlaguna.kmpballz

import androidx.compose.runtime.Composable
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import dev.jlaguna.kmpballz.ui.scenes.Navigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App() {
    initKoin { }

    setSingletonImageLoaderFactory { context ->
        createImageLoader(context)
    }

    Navigation()
}