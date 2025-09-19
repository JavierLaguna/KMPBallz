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
import dev.jlaguna.kmpballz.ui.components.TransformationAlertDialog
import dev.jlaguna.kmpballz.utils.extractColorFromImageUrl
import kmpballz.composeapp.generated.resources.Res
import kmpballz.composeapp.generated.resources.characterDetail_affiliation
import kmpballz.composeapp.generated.resources.characterDetail_characterInformation_title
import kmpballz.composeapp.generated.resources.characterDetail_description_title
import kmpballz.composeapp.generated.resources.characterDetail_gender
import kmpballz.composeapp.generated.resources.characterDetail_ki
import kmpballz.composeapp.generated.resources.characterDetail_maxKi
import kmpballz.composeapp.generated.resources.characterDetail_name
import kmpballz.composeapp.generated.resources.characterDetail_originPlanet
import kmpballz.composeapp.generated.resources.characterDetail_race
import kmpballz.composeapp.generated.resources.characterDetail_transformations_title
import kmpballz.composeapp.generated.resources.unknown
import org.jetbrains.compose.resources.stringResource

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
                    modifier = Modifier.padding(top = padding.calculateTopPadding())
                )
            }
        }
    }
}

@Composable
private fun SectionTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Start
    )
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
            Spacer(modifier = Modifier.height(16.dp))

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

            Spacer(modifier = Modifier.height(40.dp))
        }

        item {
            CharacterInformationSection(
                character = character
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            CharacterDescriptionSection(
                description = character.description
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            if (character.transformations.isNotEmpty()) {
                CharacterTransformationsSection(
                    transformations = character.transformations
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun CharacterInformationSection(
    character: Character,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionTitle(text = stringResource(Res.string.characterDetail_characterInformation_title))

        CharacterInfoItem(
            label = stringResource(Res.string.characterDetail_name),
            value = character.name
        )
        CharacterInfoItem(
            label = stringResource(Res.string.characterDetail_ki),
            value = if (character.ki.isNotEmpty()) "${character.ki}+" else stringResource(Res.string.unknown)
        )
        CharacterInfoItem(
            label = stringResource(Res.string.characterDetail_maxKi),
            value = character.maxKi
        )
        CharacterInfoItem(
            label = stringResource(Res.string.characterDetail_race),
            value = character.race
        )
        CharacterInfoItem(
            label = stringResource(Res.string.characterDetail_gender),
            value = character.gender.localized()
        )
        CharacterInfoItem(
            label = stringResource(Res.string.characterDetail_affiliation),
            value = character.affiliation
        )
        CharacterInfoItem(
            label = stringResource(Res.string.characterDetail_originPlanet),
            value = character.originPlanet?.name ?: stringResource(Res.string.unknown)
        )
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
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
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
private fun CharacterDescriptionSection(
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionTitle(text = stringResource(Res.string.characterDetail_description_title))

        Text(
            text = description,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
private fun CharacterTransformationsSection(
    transformations: List<CharacterTransformation>
) {
    var dialogTransformation by remember { mutableStateOf<CharacterTransformation?>(null) }
    val chunked = transformations.chunked(2)

    dialogTransformation?.let {
        TransformationAlertDialog(
            transformation = it,
            onDismiss = { dialogTransformation = null }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SectionTitle(text = stringResource(Res.string.characterDetail_transformations_title))

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
                            onClick = {
                                dialogTransformation = transformation
                            }
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
