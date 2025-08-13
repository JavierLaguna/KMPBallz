package dev.jlaguna.kmpballz.utils

import androidx.compose.ui.graphics.Color

fun extractColorFromImageUrl(imageUrl: String, characterName: String): Color {
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