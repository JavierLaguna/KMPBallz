package dev.jlaguna.kmpballz.ui.scenes

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.jlaguna.kmpballz.ui.scenes.characterDetail.CharacterDetailScreen
import dev.jlaguna.kmpballz.ui.scenes.charactersList.CharactersListScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf

@OptIn(KoinExperimentalAPI::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            CharactersListScreen(
                onCharacterClick = { character ->
                    navController.navigate("detail/${character.id}")
                }
            )
        }

        composable(
            "detail/{characterId}",
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val characterId = checkNotNull(backStackEntry.arguments?.getInt("characterId"))
            CharacterDetailScreen(
                vm = koinViewModel(parameters = { parametersOf(characterId) }),
                onBack = { navController.popBackStack() }
            )
        }
    }
}