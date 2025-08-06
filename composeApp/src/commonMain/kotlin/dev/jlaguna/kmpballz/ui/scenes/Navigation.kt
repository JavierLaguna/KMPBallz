package dev.jlaguna.kmpballz.ui.scenes

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.jlaguna.kmpballz.ui.scenes.characterDetail.CharacterDetailScreen
import dev.jlaguna.kmpballz.ui.scenes.charactersList.CharactersListScreen
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf

private object Routes {
    @Serializable
    object CharactersList

    @Serializable
    data class CharacterDetail(val characterId: Int)
}

@OptIn(KoinExperimentalAPI::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.CharactersList
    ) {
        composable<Routes.CharactersList> {
            CharactersListScreen(
                onCharacterClick = { character ->
                    navController.navigate(Routes.CharacterDetail(characterId = character.id))
                }
            )
        }

        composable<Routes.CharacterDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<Routes.CharacterDetail>()
            CharacterDetailScreen(
                vm = koinViewModel(parameters = { parametersOf(route.characterId) }),
                onBack = { navController.popBackStack() }
            )
        }
    }
}