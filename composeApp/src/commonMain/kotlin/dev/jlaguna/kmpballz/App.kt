package dev.jlaguna.kmpballz

import androidx.compose.runtime.Composable
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import dev.jlaguna.kmpballz.ui.theme.Theme
import dev.jlaguna.kmpballz.utils.createImageLoader
import dev.jlaguna.kmpballz.di.initKoin
import dev.jlaguna.kmpballz.ui.scenes.mainTabBar.MainTabBar
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App() {
    initKoin { }

    setSingletonImageLoaderFactory { context ->
        createImageLoader(context)
    }

    Theme(true) { // TODO: JLI - Remove theme mock
        MainTabBar()
    }
}