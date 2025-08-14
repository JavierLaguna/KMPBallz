package dev.jlaguna.kmpballz.ui.scenes.characterDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.jlaguna.kmpballz.business.useCases.models.Character
import dev.jlaguna.kmpballz.ui.components.LoadingIndicator
import dev.jlaguna.kmpballz.ui.components.Screen
import dev.jlaguna.kmpballz.ui.components.BackTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    vm: CharacterDetailViewModel,
    onBack: () -> Unit
) {
    val state by vm.state.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Screen {
        Scaffold(
            topBar = {
                BackTopBar(
                    title = state.character?.name ?: "",
                    scrollBehavior = scrollBehavior,
                    onBack = onBack
                )
            }
        ) { padding ->
            // TODO: JLI - Fix
//            LoadingIndicator(
//                enabled = state.isLoading,
//                modifier = Modifier.padding(padding)
//            )

            state.character?.let { character ->
                CharacterDetail(
                    character,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
private fun CharacterDetail(
    character: Character,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            Modifier.padding(horizontal = 24.dp)
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(300.dp)
            )

            Column(
                Modifier.padding(horizontal = 8.dp)
            ) {
                Text(character.ki)
                Text(character.maxKi)
            }
        }

        Text(text = character.description, modifier = Modifier.padding(16.dp))
    }
}