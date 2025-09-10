package dev.jlaguna.kmpballz.ui.scenes.characterDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import dev.jlaguna.kmpballz.business.models.Character
import dev.jlaguna.kmpballz.business.models.CharacterTransformation
import dev.jlaguna.kmpballz.ui.components.BackTopBar
import dev.jlaguna.kmpballz.ui.components.CharacterCard
import dev.jlaguna.kmpballz.ui.components.Screen
import dev.jlaguna.kmpballz.utils.extractColorFromImageUrl

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
            },
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
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
private fun CharacterDetail(
    character: Character,
    modifier: Modifier = Modifier
) {
    var backgroundColor by remember { mutableStateOf(Color.Transparent) }

    LaunchedEffect(character.image) {
        val extractedColor = extractColorFromImageUrl(character.image, character.name)
        backgroundColor = extractedColor
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .shadow(8.dp, CircleShape)
                    .clip(CircleShape)
                    .background(backgroundColor)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = character.image,
                    contentDescription = "${character.name} image",
                    contentScale = ContentScale.FillHeight
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Character Information",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CharacterInfoItem(label = "Name", value = character.name)
                CharacterInfoItem(label = "Ki", value = if (character.ki.isNotEmpty()) "${character.ki}+" else "Unknown")
                CharacterInfoItem(label = "Race", value = character.race)
                CharacterInfoItem(label = "Gender", value = character.gender.displayName)
                CharacterInfoItem(label = "Affiliation", value = character.affiliation)
                CharacterInfoItem(label = "Planet of Origin", value = "Earth")
            }

            Spacer(modifier = Modifier.height(32.dp))
        }

        item {
            CharacterTransformationsSection(transformations = character.transformations)
        }
    }
}

@Composable
private fun CharacterInfoItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier.fillMaxWidth()
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onTertiary
            )

            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
            )
        }

        HorizontalDivider(
            Modifier.padding(top = 12.dp),
            1.dp,
            Color.Gray.copy(alpha = 0.3f)
        )
    }
}

@Composable
private fun CharacterTransformationsSection(
    transformations: List<CharacterTransformation>
) {
    val chunked = transformations.chunked(2)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        chunked.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { transformation ->
                    Box(modifier = Modifier.weight(1f)) {
                        CharacterCard(
                            characterName = transformation.name,
                            characterImage = transformation.image,
                        )
                    }
                }

                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
