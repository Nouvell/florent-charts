package io.nouvell.florentcharts.util

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill

internal fun DrawScope.drawRoundedTopRect(
    brush: Brush,
    topLeft: Offset = Offset.Zero,
    size: Size = Size(
        this.size.width - topLeft.x,
        this.size.height - topLeft.y
    ),
    cornerRadius: CornerRadius = CornerRadius.Zero,
    /*FloatRange(from = 0.0, to = 1.0)*/
    alpha: Float = 1.0f,
    style: DrawStyle = Fill,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode
) {
    val offsetX = topLeft.x
    val offsetY = topLeft.y

    val rx = cornerRadius.x.coerceAtMost(size.width / 2)
    val ry = cornerRadius.y.coerceAtMost(size.width / 2)

    val path = Path().apply {
        this.moveTo(offsetX, offsetY + ry)
        this.relativeQuadraticBezierTo(0f, -ry, -rx, -ry)
        this.relativeLineTo(-(size.width - (2 * rx)), 0f)
        this.relativeQuadraticBezierTo(-rx, 0f, -rx, ry)
        this.relativeLineTo(0f, (size.height - ry))
        this.relativeLineTo(size.width, 0f)
        close()
    }

    drawPath(path, brush, alpha, style, colorFilter, blendMode)
}