package com.example.foodapps.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToInt
import androidx.compose.ui.util.lerp

val inactiveTrackColor:Color = Color(0xFFE5ECFB)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceSlider(
    modifier: Modifier = Modifier,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 0f..70f,
    thumbColor: Color = Color(0xFF7A64FF),
    activeTrackColor: Color = Color(0xFF7A64FF),
    inactiveTrackColor: Color = Color(0xFFE5ECFB),
    sliderPadding: Dp = 12.dp,
    thumbSize: Dp = 27.dp,
) {
    val currencyFormatter = remember {
        NumberFormat.getCurrencyInstance(Locale.US).apply {
            maximumFractionDigits = 2
            minimumFractionDigits = 2
        }
    }
    @Suppress("UnusedBoxWithConstraintsScope")
    BoxWithConstraints(modifier = modifier) {
        val density = LocalDensity.current
        val constraintsWidth = constraints.maxWidth
        val sliderWidth = constraintsWidth - (sliderPadding.value * density.density * 2).roundToInt()
        val minLabel = currencyFormatter.format(valueRange.start)
        val currentLabel = currencyFormatter.format(value)

        Column {
            SliderLabels(
                minLabel = minLabel,
                currentLabel = currentLabel,
                sliderPadding = sliderPadding,
                sliderWidth = sliderWidth.toInt(),
                value = value,
                valueRange = valueRange
            )

            CustomSlider(
                value = value,
                onValueChange = onValueChange,
                valueRange = valueRange,
                thumbColor = thumbColor,
                activeTrackColor = activeTrackColor,
                sliderPadding = sliderPadding,
                thumbSize = thumbSize
            )
        }
    }
}

@Composable
private fun SliderLabels(
    minLabel: String,
    currentLabel: String,
    sliderPadding: Dp,
    sliderWidth: Int,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>
) {
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(bottom = 4.dp)
    ) {
        Text(
            text = minLabel,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = sliderPadding)
        )

        Text(
            text = currentLabel,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset { calculateTextOffset(sliderWidth, value, valueRange, textLayoutResult, density) },
            onTextLayout = { textLayoutResult = it }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    thumbColor: Color,
    activeTrackColor: Color,
    sliderPadding: Dp,
    thumbSize: Dp
) {
    val interactionSource = remember { MutableInteractionSource() }

    Slider(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = sliderPadding),
        valueRange = valueRange,
        interactionSource = interactionSource,
        colors = SliderDefaults.colors(
            thumbColor = thumbColor,
            activeTrackColor = inactiveTrackColor,
            inactiveTrackColor = inactiveTrackColor,
        ),
        thumb = {
            SliderDefaults.Thumb(
                interactionSource = interactionSource,
                modifier = Modifier.size(thumbSize),
                colors = SliderDefaults.colors(thumbColor = thumbColor)
            )
        },
        track = { sliderPositions ->
            SliderTrack(
                sliderPositions = sliderPositions,
                activeColor = activeTrackColor,
                value = value,
                valueRange = valueRange
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SliderTrack(
    sliderPositions: SliderState,
    activeColor: Color,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    trackHeight: Dp = 8.dp // Add track height parameter
) {
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(trackHeight)) {
        val trackWidth = size.width
        val trackYCenter = center.y
        val fraction = calculateProgressFraction(value, valueRange)
        val thumbCenterX = lerp(0f, trackWidth, fraction)

        drawLine(
            color = activeColor,
            strokeWidth = size.height,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = trackYCenter),
            end = Offset(x = thumbCenterX, y = trackYCenter)
        )
    }
}

private fun calculateProgressFraction(
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>
): Float {
    val range = valueRange.endInclusive - valueRange.start
    return if (range > 0) ((value - valueRange.start) / range).coerceIn(0f, 1f) else 0f
}

private fun calculateTextOffset(
    sliderWidth: Int,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    textLayoutResult: TextLayoutResult?,
    density: androidx.compose.ui.unit.Density
): IntOffset {
    val range = valueRange.endInclusive - valueRange.start
    val valueRatio = if (range > 0) ((value - valueRange.start) / range).coerceIn(0f, 1f) else 0f
    val thumbOffsetPx = valueRatio * sliderWidth
    val halfTextWidthPx = (textLayoutResult?.size?.width ?: 0) / 2
    val textOffsetXPx = thumbOffsetPx - halfTextWidthPx

    val maxTextOffsetPx = sliderWidth - (textLayoutResult?.size?.width ?: 0)
    val minTextOffsetPx = with(density) { 16.dp.roundToPx() } // Use sliderPadding value here

    return IntOffset(
        x = when {
            maxTextOffsetPx <= minTextOffsetPx -> minTextOffsetPx
            textOffsetXPx <= minTextOffsetPx -> minTextOffsetPx
            textOffsetXPx >= maxTextOffsetPx -> maxTextOffsetPx
            else -> textOffsetXPx.toInt()
        },
        y = 0
    )
}