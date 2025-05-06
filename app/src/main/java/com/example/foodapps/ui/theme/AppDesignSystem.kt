package com.example.foodapps.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

@Stable
data class AppColorScheme(
    val background: Color,
    val onBackground: Color,
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val separator: Color,
    val error: Color,
   val loginTitle : Color,
   val searchText : Color,
   val brandColor : Color,
   val scrimColor : Color,
   val white : Color
) {

    val primaryHorizontalGradient: Brush = Brush.horizontalGradient(listOf(primary, secondary))
}

@Stable
data class AppTypography(
    val titleLarge: TextStyle,
    val titleNormal: TextStyle,
    val paragraph: TextStyle,
    val labelLarge: TextStyle,
    val labelNormal: TextStyle,
    val labelSmall: TextStyle
)

@Stable
data class AppShape(
    val container: Shape,
    val button: Shape,
    val circular: Shape
)

@Stable
data class AppSize(
    val large: Dp,
    val medium: Dp,
    val normal: Dp,
    val small: Dp,
    val extrasmall: Dp
)

val LocalAppColorScheme = staticCompositionLocalOf {
    AppColorScheme(
        background = Color.Unspecified,
        onBackground = Color.Unspecified,
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        secondary = Color.Unspecified,
        onSecondary = Color.Unspecified,
        separator = Color.Unspecified,
        error = Color.Unspecified,
        loginTitle = Color.Unspecified,
        searchText = Color.Unspecified,
        brandColor = Color.Unspecified,
        scrimColor = Color.Unspecified,
        white = Color.Unspecified,
    )
}

val LocalAppTypography = staticCompositionLocalOf {
    AppTypography(
        titleLarge = TextStyle.Default,
        titleNormal = TextStyle.Default,
        paragraph = TextStyle.Default,
        labelLarge = TextStyle.Default,
        labelNormal = TextStyle.Default,
        labelSmall = TextStyle.Default
    )
}

@Composable
fun DpExample(size: Dp) {
    Box(
        modifier = Modifier
            .size(size) // Use Dp for size
            .background(Color.Blue)
    )
}

val LocalAppShape = staticCompositionLocalOf {
    AppShape(
        container = RectangleShape,
        button = RectangleShape,
        circular = CircleShape
    )
}

val LocalAppSize = staticCompositionLocalOf {
    AppSize(
        large = Dp.Unspecified,
        medium = Dp.Unspecified,
        normal = Dp.Unspecified,
        small = Dp.Unspecified,
        extrasmall = Dp.Unspecified,
    )
}