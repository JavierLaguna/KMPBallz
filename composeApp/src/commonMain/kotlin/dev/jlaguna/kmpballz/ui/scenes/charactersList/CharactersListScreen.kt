package dev.jlaguna.kmpballz.ui.scenes.charactersList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.jlaguna.kmpballz.business.models.Character
import dev.jlaguna.kmpballz.ui.UIState
import dev.jlaguna.kmpballz.ui.components.CharacterCard
import dev.jlaguna.kmpballz.ui.components.ErrorView
import dev.jlaguna.kmpballz.ui.components.LoadingIndicator
import dev.jlaguna.kmpballz.ui.components.Screen
import kmpballz.composeapp.generated.resources.Res
import kmpballz.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun CharactersListScreen(
    viewModel: CharactersListViewModel = koinViewModel(),
    onCharacterClick: (Character) -> Unit = {}
) {
    val charactersState by viewModel.characters.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.handleEvent(CharactersListContract.Event.OnLoadView)
    }

    Screen {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(Res.string.app_name)) },
                    scrollBehavior = scrollBehavior
                )
            },
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { padding ->
            if (charactersState.state == UIState.State.ERROR) {
                ErrorView(
                    error = charactersState.error,
                    onRetryClick = {
                        viewModel.handleEvent(CharactersListContract.Event.OnPressRetry)
                })

            } else {
                LoadingIndicator(
                    enabled = charactersState.isLoading && !charactersState.isEmpty,
                    modifier = Modifier.padding(padding)
                )

                charactersState.data?.let { characters ->
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(characters) { it ->
                            CharacterCard(
                                characterName = it.name,
                                characterImage = it.image,
                                onClick = { onCharacterClick(it) }
                            )
                        }

                        item(
                            span = { GridItemSpan(2) }
                        ) {
                            LaunchedEffect(Unit) {
                                viewModel.handleEvent(CharactersListContract.Event.OnEndScroll)
                            }
                            Spacer(modifier = Modifier.height(1.dp))
                        }
                    }
                }
            }
        }
    }
}