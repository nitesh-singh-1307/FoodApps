package com.example.foodapps.common

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.random.Random
import androidx.compose.ui.platform.LocalDensity // <-- Import for Density
import kotlin.div

@Composable
fun ConfettiAnimation(
    modifier: Modifier = Modifier,
    particleCount: Int = 100,
    animationDuration: Int = 8000 // 3 seconds
){
    val density = LocalDensity.current
    val screenWidth = with(density) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
    val screenHeight = with(density) { LocalConfiguration.current.screenHeightDp.dp.toPx() }
    // State to hold particles
    var particles by remember { mutableStateOf(createParticles(particleCount, screenWidth, screenHeight)) }

    // Animation state for each particle
    val animatables = particles.map { particle ->
        remember { Animatable(0f) }
    }
    // Trigger animation
    LaunchedEffect(Unit) {
        animatables.forEachIndexed { index, animatable ->
            launch {
                animatable.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = animationDuration, easing = LinearEasing)
                )
                // Reset particles after animation
                particles = createParticles(particleCount, screenWidth, screenHeight)
            }
        }
    }

    // Draw particles
    Canvas(modifier = modifier.fillMaxSize()) {
        particles.forEachIndexed { index, particle ->
            val progress = animatables[index].value
            val newY = particle.y + particle.velocityY * progress * screenHeight
            val newX = particle.x + particle.velocityX * progress * screenWidth
            val newAlpha = 1f - progress // Fade out as progress increases

            if (newY < screenHeight) { // Only draw if particle is on screen

                drawRoundRect(
                    color = particle.color,
                    topLeft = Offset(newX - particle.size / 2, newY - particle.size / 2), // Adjust top-left for center
                    size = Size(particle.size, particle.size),
                    cornerRadius = CornerRadius(particle.size / 2, particle.size / 2), // Circular shape
                    alpha = newAlpha
                )
            }
        }
    }

}

private fun createParticles(count: Int, screenWidth: Float, screenHeight: Float): List<Particle> {
    val random = Random.Default
//    val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Magenta, Color.Yellow, Color.Cyan)
    val colors = listOf(Color(0xFFf44336), Color(0xFFe91e63), Color(0xFF9c27b0), Color(0xFF673ab7),
    Color(0xFF3f51b5), Color(0xFF2196f3), Color(0xFF03a9f4), Color(0xFF00bcd4),
    Color(0xFF009688), Color(0xFF4caf50), Color(0xFF8bc34a), Color(0xFFcddc39),
    Color(0xFFffeb3b), Color(0xFFffc107), Color(0xFFff9800), Color(0xFFff5722))

    return List(count) {
        Particle(
            x = random.nextFloat() * screenWidth,
            y = -random.nextFloat() * screenHeight / 2, // Start above the screen
            velocityX = random.nextFloat() * 4 - 2, // Random horizontal velocity (-2 to 2)
            velocityY = random.nextFloat() * 5 + 2, // Random downward velocity (2 to 7)
            color = colors.random(),
            size = random.nextFloat() * 10 + 5, // Size between 5 and 15
            alpha = 1f
        )
    }
}