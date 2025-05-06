package com.example.foodapps.common
import androidx.compose.ui.graphics.Color // Import for Compose Color


data class Particle( val x: Float,
                     val y: Float,
                     val velocityX: Float,
                     val velocityY: Float,
                     val color: Color,
                     val size: Float,
                     val alpha: Float)
