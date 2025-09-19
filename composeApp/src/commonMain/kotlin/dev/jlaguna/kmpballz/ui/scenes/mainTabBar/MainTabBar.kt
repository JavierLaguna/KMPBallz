package dev.jlaguna.kmpballz.ui.scenes.mainTabBar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import dev.jlaguna.kmpballz.ui.scenes.routers.CharactersNavHost
import kmpballz.composeapp.generated.resources.Res
import kmpballz.composeapp.generated.resources.tabBar_characters
import kmpballz.composeapp.generated.resources.tabBar_planets
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

enum class BottomTab(val labelRes: StringResource, val icon: ImageVector) {
    Characters(Res.string.tabBar_characters, Icons.Default.Group),
    Planets(Res.string.tabBar_planets, Icons.Default.Public)
}

@Composable
fun MainTabBar() {

    var selectedTab by remember { mutableStateOf(BottomTab.Characters) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                BottomTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        icon = { Icon(tab.icon, contentDescription = null) },
                        label = { Text(stringResource(tab.labelRes)) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            indicatorColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
            }
        }
    ) { padding ->
        when (selectedTab) {
            BottomTab.Characters -> CharactersNavHost(
                Modifier.padding(bottom = padding.calculateBottomPadding())
            )

            BottomTab.Planets -> PlanetsScreen(
                Modifier.padding(bottom = padding.calculateBottomPadding())
            )
        }
    }
}

@Composable
private fun PlanetsScreen(modifier: Modifier = Modifier) {
    Text("Planets screen", modifier = modifier)
}