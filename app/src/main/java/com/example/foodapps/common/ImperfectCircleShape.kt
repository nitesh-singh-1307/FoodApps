package com.example.foodapps.common

import android.graphics.Matrix
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.graphics.PathParser

class ImperfectCircleShape : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = PathParser.createPathFromPathData(
                "M256.348 170.923C250.501 187.464 239.892 201.828 228.886 214.097C217.516 226.877 203.772 237.308 188.425 244.806C173.281 252.066 156.318 258.008 139.297 258.921C122.873 259.802 106.43 253.11 89.9612 247.265C73.4921 241.421 56.2824 236.496 44.0622 225.447C31.3872 213.999 20.96 198.99 13.7292 183.786C6.49838 168.581 1.7623 150.989 0.852762 133.899C0.709835 131.208 0.66003 128.479 0.703341 125.713C0.92403 111.119 3.49078 96.6569 8.30445 82.8847C14.1515 66.3494 26.4952 53.5648 37.5395 41.2954C48.9412 28.5695 61.2264 16.5089 76.3702 9.2425C91.5139 1.97612 108.646 1.12163 125.667 0.208441C142.244 -0.757879 158.845 1.64253 174.477 7.2661C190.946 13.1366 204.972 21.1531 217.192 32.1962C229.848 43.6306 242.588 55.3716 249.819 70.5697C257.049 85.7677 259.869 102.988 260.772 120.084C260.856 121.61 260.934 123.156 261.006 124.709C261.694 139.868 261.629 155.927 256.348 170.923Z"
            ).asComposePath().apply {
                val pathSize = getBounds().size
                val matrix = Matrix()
                matrix.postScale(
                    size.width / pathSize.width,
                    size.height / pathSize.height
                )
                asAndroidPath().transform(matrix)
                val left = getBounds().left
                val top = getBounds().top
                translate(Offset(-left, -top))
            }
        )
    }
}