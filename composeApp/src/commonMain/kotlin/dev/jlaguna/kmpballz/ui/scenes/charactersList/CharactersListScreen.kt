package dev.jlaguna.kmpballz.ui.scenes.charactersList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import dev.jlaguna.kmpballz.data.models.Character
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun CharactersListScreen(
    viewModel: CharactersListViewModel = koinViewModel(),
    onNavigateBack: () -> Unit = {},
    onCharacterClick: (Character) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onUiReady()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D2D2D))
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Characters",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF2D2D2D)
            )
        )

        // Content
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color(0xFFFF9500)
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.characters) { character ->
                    CharacterCard(
                        character = character,
                        onClick = { onCharacterClick(character) }
                    )
                }
            }
        }
    }
}

@Composable
fun CharacterCard(
    character: Character,
    onClick: () -> Unit
) {
    var backgroundColor by remember { mutableStateOf(getOptimalBackgroundColor(character.name)) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.8f)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Character Image
            CharacterImage(
                imageUrl = character.image,
                characterName = character.name,
                onColorExtracted = { color ->
                    backgroundColor = color
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )

            // Gradient overlay for better text readability
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            )
                        )
                    )
            )

            // Character Name
            Text(
                text = character.name,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            )
        }
    }
}



// Custom composable to handle image loading with simple color estimation
@Composable
fun CharacterImage(
    imageUrl: String,
    characterName: String,
    onColorExtracted: (Color) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    // Extract color from image URL or character name when image loads successfully
    LaunchedEffect(imageUrl) {
        // Simple heuristic: extract color based on character name and image URL patterns
        val extractedColor = extractColorFromImageUrl(imageUrl, characterName)
        onColorExtracted(extractedColor)
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when {
            isError -> {
                // Error state - show character initial
                Text(
                    text = characterName.first().uppercase(),
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            isLoading -> {
                // Loading state
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        AsyncImage(
            model = imageUrl,
            contentDescription = characterName,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit,
            onLoading = { isLoading = true },
            onSuccess = {
                isLoading = false
                isError = false
            },
            onError = {
                isLoading = false
                isError = true
            }
        )
    }
}

// KMP-compatible function to extract/estimate optimal colors
private fun extractColorFromImageUrl(imageUrl: String, characterName: String): Color {
    // Analyze image URL for color hints (many Dragon Ball APIs include color info in URLs)
    val urlLower = imageUrl.lowercase()

    // Check for color keywords in URL
    val colorFromUrl = when {
        urlLower.contains("orange") || urlLower.contains("goku") -> Color(0xFF8B4513) // Saddle brown
        urlLower.contains("blue") || urlLower.contains("vegeta") -> Color(0xFF1E3A8A) // Dark blue
        urlLower.contains("green") || urlLower.contains("piccolo") -> Color(0xFF2D5A2B) // Dark green
        urlLower.contains("purple") || urlLower.contains("frieza") -> Color(0xFF4C1D5A) // Dark purple
        urlLower.contains("yellow") || urlLower.contains("gohan") -> Color(0xFF7A6A1F) // Dark yellow
        urlLower.contains("pink") || urlLower.contains("buu") -> Color(0xFF8B4A6B) // Dark pink
        urlLower.contains("red") -> Color(0xFF7A1F1F) // Dark red
        else -> null
    }

    if (colorFromUrl != null) return colorFromUrl

    // Fallback to character-based color mapping
    return getOptimalBackgroundColor(characterName)
}

// Enhanced character-based color mapping with more Dragon Ball characters
private fun getOptimalBackgroundColor(characterName: String): Color {
    return when (characterName.lowercase()) {
        "goku", "son goku", "kakarot" -> Color(0xFF8B4513) // Saddle brown (gi color)
        "vegeta" -> Color(0xFF1E3A8A) // Dark blue (armor)
        "piccolo" -> Color(0xFF2D5A2B) // Dark green (skin)
        "gohan", "son gohan" -> Color(0xFF4A4A4A) // Dark gray (school uniform)
        "frieza", "freezer" -> Color(0xFF4C1D5A) // Dark purple (body)
        "cell" -> Color(0xFF2D4A2D) // Dark green (body)
        "majin buu", "buu" -> Color(0xFF8B4A6B) // Dark pink (skin)
        "trunks" -> Color(0xFF1E40AF) // Blue (jacket)
        "goten" -> Color(0xFF7A6A1F) // Dark yellow/orange (gi)
        "krillin", "kuririn" -> Color(0xFF8B4513) // Orange (gi)
        "yamcha" -> Color(0xFF4A4A4A) // Gray (outfit)
        "tien", "tenshinhan" -> Color(0xFF2D4A2D) // Green (outfit)
        "android 17" -> Color(0xFF1F1F1F) // Dark (outfit)
        "android 18" -> Color(0xFF1E3A8A) // Blue (denim)
        "broly" -> Color(0xFF2D5A2B) // Dark green
        "bardock" -> Color(0xFF8B4513) // Brown (armor)
        "raditz" -> Color(0xFF4A4A4A) // Dark gray (armor)
        "nappa" -> Color(0xFF8B4513) // Brown (armor)
        "ginyu" -> Color(0xFF4C1D5A) // Purple (body)
        "captain ginyu" -> Color(0xFF4C1D5A) // Purple
        "dodoria" -> Color(0xFF8B4A6B) // Pink (skin)
        "zarbon" -> Color(0xFF2D5A2B) // Green (skin)
        "king cold" -> Color(0xFF4C1D5A) // Purple (body)
        "cooler" -> Color(0xFF2D4A2D) // Dark green/purple
        "janemba" -> Color(0xFF7A1F1F) // Dark red
        "super buu" -> Color(0xFF8B4A6B) // Pink
        "kid buu" -> Color(0xFF8B4A6B) // Pink
        else -> Color(0xFF374151) // Default dark gray
    }
}