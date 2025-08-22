package dev.jlaguna.kmpballz.ui.scenes.characterDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import dev.jlaguna.kmpballz.business.models.Character
import dev.jlaguna.kmpballz.ui.components.BackTopBar
import dev.jlaguna.kmpballz.ui.components.Screen

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetail(
    character: Character,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Character Image with gradient background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.9f),
                            Color.Black.copy(alpha = 0.7f),
                            Color.Transparent
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = "${character.name} image",
                modifier = modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Character Information Title
        Text(
            text = "Character Information",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Information Fields
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CharacterInfoItem(
                label = "Name",
                value = character.name
            )

            CharacterInfoItem(
                label = "Ki",
                value = if (character.ki.isNotEmpty()) "${character.ki}+" else "Unknown"
            )

            CharacterInfoItem(
                label = "Race",
                value = character.race
            )

            CharacterInfoItem(
                label = "Gender",
                value = character.gender.displayName
            )

            CharacterInfoItem(
                label = "Affiliation",
                value = character.affiliation
            )

            CharacterInfoItem(
                label = "Planet of Origin",
                value = "Earth" // Puedes extraer esto de description o agregar un campo en el modelo
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }

}

@Composable
private fun CharacterInfoItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )

        Divider(
            modifier = Modifier.padding(top = 12.dp),
            color = Color.Gray.copy(alpha = 0.3f),
            thickness = 1.dp
        )
    }
}