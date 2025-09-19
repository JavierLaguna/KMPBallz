package dev.jlaguna.kmpballz.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kmpballz.composeapp.generated.resources.Res
import kmpballz.composeapp.generated.resources.four_stars_dragon_ball
import kmpballz.composeapp.generated.resources.loading
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoadingIndicator(
    enabled: Boolean,
    showText: Boolean = true,
    loadingText: String = stringResource(Res.string.loading),
    animationType: AnimationType = AnimationType.entries.random(),
    modifier: Modifier = Modifier,
) {
    if (enabled) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (animationType) {
                AnimationType.ROTATION -> RotatingDragonBall()
                AnimationType.PULSING -> PulsingDragonBall()
                AnimationType.BOUNCING -> BouncingDragonBall()
                AnimationType.FLOATING -> FloatingDragonBall()
                AnimationType.COMBINED -> CombinedAnimationDragonBall()
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (showText) {
                Text(
                    text = loadingText,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

enum class AnimationType {
    ROTATION,
    PULSING,
    BOUNCING,
    FLOATING,
    COMBINED
}

@Composable
private fun RotatingDragonBall() {
    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Image(
        painter = painterResource(Res.drawable.four_stars_dragon_ball), // Cambia por tu recurso
        contentDescription = "Loading",
        modifier = Modifier
            .size(80.dp)
            .rotate(rotation)
    )
}

@Composable
private fun PulsingDragonBall() {
    val infiniteTransition = rememberInfiniteTransition(label = "pulsing")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Image(
        painter = painterResource(Res.drawable.four_stars_dragon_ball),
        contentDescription = "Loading",
        modifier = Modifier
            .size(80.dp)
            .scale(scale)
    )
}

@Composable
private fun BouncingDragonBall() {
    val infiniteTransition = rememberInfiniteTransition(label = "bouncing")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -30f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offsetY"
    )

    Image(
        painter = painterResource(Res.drawable.four_stars_dragon_ball),
        contentDescription = "Loading",
        modifier = Modifier
            .size(80.dp)
            .graphicsLayer {
                translationY = offsetY
            }
    )
}

@Composable
private fun FloatingDragonBall() {
    val infiniteTransition = rememberInfiniteTransition(label = "floating")

    val offsetY by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floatingY"
    )

    val offsetX by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floatingX"
    )

    Image(
        painter = painterResource(Res.drawable.four_stars_dragon_ball),
        contentDescription = "Loading",
        modifier = Modifier
            .size(80.dp)
            .graphicsLayer {
                translationY = offsetY
                translationX = offsetX
            }
    )
}

@Composable
private fun CombinedAnimationDragonBall() {
    val infiniteTransition = rememberInfiniteTransition(label = "combined")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val offsetY by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offsetY"
    )

    Image(
        painter = painterResource(Res.drawable.four_stars_dragon_ball),
        contentDescription = "Loading",
        modifier = Modifier
            .size(80.dp)
            .rotate(rotation)
            .scale(scale)
            .graphicsLayer {
                translationY = offsetY
            }
    )
}
