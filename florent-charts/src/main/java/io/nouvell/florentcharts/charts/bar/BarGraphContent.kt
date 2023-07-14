package io.nouvell.florentcharts.charts.bar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nouvell.florentcharts.model.BarGraphGroup
import io.nouvell.florentcharts.util.drawRoundedTopRect
import kotlin.math.ceil

@Composable
fun BarGraphContent(
    dataPoints: List<BarGraphGroup>,
    barThickness: Dp,
    graphLineColor: Color,
    labelTextColor: Color,
    labelTextStyle: TextStyle,
    averageLineColor: Color,
    modifier: Modifier = Modifier,
) {

    val dataMaximumPoint = remember(dataPoints) {
        val dataMax = dataPoints.maxOf { it.totalValue }
        dataMax + (dataMax * 0.05)
    }

    val dataAverage = remember(dataPoints) {
        dataPoints.sumOf { it.totalValue } / dataPoints.size
    }

    val dataMaxPointString = remember(dataMaximumPoint) {
        val x = dataMaximumPoint / 1000f
        val ceilX = ceil(x).toInt()
        val trailingChar = if (x >= 1.0) 'k' else null

        if (x >= 1) "${ceilX}${trailingChar ?: ""}"
        else ceil(dataMaximumPoint).toInt().toString()
    }

    val density = LocalDensity.current

    val graphLineBrush = remember { SolidColor(graphLineColor) }
    val graphAreaInsetSize = remember { with(density) { 16.dp.toPx() } }
    val lineThickness = remember { with(density) { 1.dp.toPx() } }
    val barThicknessPx = remember { with(density) { barThickness.toPx() } }
    val borderRadiusPx = remember { with(density) { 3.dp.toPx() } }

    val labelTextPadding = remember { 10f }
    val xAxisLabelTextSize = remember { with(density) { labelTextStyle.fontSize.toPx() } }
    val averageLabelTextSize =
        remember { with(density) { labelTextStyle.fontSize.toPx() - 1.sp.toPx() } }
    val yAxisLabelTextSize =
        remember { with(density) { labelTextStyle.fontSize.toPx() - 2.sp.toPx() } }

    val xAxisLabelPaint = remember {
        Paint().apply {
            this.color = labelTextColor
        }.asFrameworkPaint().apply {
            this.textSize = xAxisLabelTextSize
            this.textAlign = android.graphics.Paint.Align.CENTER
        }
    }

    val yAxisLabelPaint = remember {
        Paint().apply {
            this.color = labelTextColor
        }.asFrameworkPaint().apply {
            this.textSize = yAxisLabelTextSize
        }
    }

    val averageLabelPaint = remember {
        Paint().apply {
            this.color = Color(0xFFCC2F26)
        }.asFrameworkPaint().apply {
            this.textSize = averageLabelTextSize
        }
    }

    val yAxisLabelMaxWidth = remember(averageLabelPaint, yAxisLabelPaint) {
        val avgLabelLength = averageLabelPaint.measureText("avg")
        val yLabelLength = yAxisLabelPaint.measureText(dataMaxPointString)

        maxOf(avgLabelLength, yLabelLength)
    }

    val averageLineBrush = remember { SolidColor(averageLineColor) }

    val graphAreaRightPadding = remember(graphAreaInsetSize, yAxisLabelMaxWidth) {
        graphAreaInsetSize + yAxisLabelMaxWidth
    }

    val xAxisLabelMaxWidth = remember(xAxisLabelPaint, dataPoints, density) {
        dataPoints.maxOf {
            xAxisLabelPaint.measureText(it.label)
        }
    }

    val requiredGraphCanvasWidth = remember(dataPoints, xAxisLabelMaxWidth) {
        val xlp = with(density) { 10.dp.toPx() } // x-axis label padding
        val bp = with(density) { 25.dp.toPx() } // bar padding

        val bw = (barThicknessPx + bp.toDouble()) // bar width
        val lw = (xAxisLabelMaxWidth + xlp.toDouble()) // label width

        val slotWidth = maxOf(bw, lw)
        slotWidth * dataPoints.size
    }

    BoxWithConstraints(modifier) {
        val maxWidthPx = with(density) { maxWidth.toPx() }
        val rightCanvasWidth = with(density) { graphAreaRightPadding.toDp() }

        val availableGraphCanvasWidth = maxWidthPx - graphAreaRightPadding

        val isCanvasSufficient = remember(requiredGraphCanvasWidth, availableGraphCanvasWidth) {
            availableGraphCanvasWidth >= requiredGraphCanvasWidth
        }

        val computedGraphCanvasWidth = remember(isCanvasSufficient) {
            val width = if (isCanvasSufficient) availableGraphCanvasWidth
            else (requiredGraphCanvasWidth)

            with(density) { width.toFloat().toDp() }
        }

        Row {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Canvas(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .fillMaxHeight()
                        .width(computedGraphCanvasWidth),
                ) {
                    // x-axis and y-axis divider lines
                    inset(
                        top = graphAreaInsetSize,
                        bottom = graphAreaInsetSize + xAxisLabelTextSize,
                        left = graphAreaInsetSize,
                        right = 0f,
                    ) {
                        val insetCanvasWidth = this.size.width
                        val insetCanvasHeight = this.size.height

                        // x-axis line
                        drawLine(
                            graphLineBrush,
                            Offset(0f, insetCanvasHeight),
                            Offset(insetCanvasWidth, insetCanvasHeight),
                            lineThickness,
                        )

                        // y-axis divider lines
                        (0..3).forEach { pos ->
                            val yOffset = (insetCanvasHeight / 3) * pos

                            drawLine(
                                graphLineBrush,
                                Offset(0f, yOffset),
                                Offset(insetCanvasWidth, yOffset),
                                lineThickness,
                            )
                        }

                        val averageBarRectHeight =
                            (dataAverage / dataMaximumPoint) * this.size.height
                        val averageLineOffsetY = (this.size.height - averageBarRectHeight).toFloat()

                        // average graph line
                        drawLine(
                            averageLineBrush,
                            Offset(0f, averageLineOffsetY),
                            Offset(this.size.width, averageLineOffsetY),
                            lineThickness,
                            pathEffect = PathEffect.dashPathEffect(
                                floatArrayOf(10f, 20f),
                            )
                        )
                    }

                    val barSlotWidth = (this.size.width - graphAreaInsetSize) / dataPoints.size
                    val barSlotCenter = barSlotWidth / 2

                    // x -axis text label area
                    inset(
                        top = 0f,
                        left = graphAreaInsetSize,
                        right = graphAreaRightPadding,
                        bottom = 0f
                    ) {
                        dataPoints.forEachIndexed { index, group ->

                            val xPos = index + 1
                            val xAxisLabelOffsetX = (xPos * barSlotWidth) - barSlotCenter

                            inset(
                                top = graphAreaInsetSize,
                                bottom = graphAreaInsetSize + xAxisLabelTextSize,
                                left = 0f,
                                right = 0f
                            ) {
                                val xAxisGridLineOffsetX = barSlotWidth * index
                                val xAxisLabelOffsetY =
                                    this.size.height + labelTextPadding + xAxisLabelTextSize

                                // text label
                                drawIntoCanvas {
                                    it.nativeCanvas.drawText(
                                        group.label,
                                        xAxisLabelOffsetX,
                                        xAxisLabelOffsetY,
                                        xAxisLabelPaint
                                    )
                                }

                                // x-axis divider lines
                                drawLine(
                                    graphLineBrush,
                                    Offset(xAxisGridLineOffsetX, 0f),
                                    Offset(xAxisGridLineOffsetX, this.size.height),
                                    lineThickness,
                                )


                                val groupTotalValue = group.totalValue
                                val totalRectHeight =
                                    (groupTotalValue / dataMaximumPoint) * this.size.height
                                val rectOffsetX =
                                    (xPos * barSlotWidth) - ((barSlotWidth - barThicknessPx) / 2)
                                val totalRectOffsetY =
                                    (this.size.height - totalRectHeight).toFloat()
                                val groupSections = group.sections
                                val prevHeights = mutableListOf<Double>()

                                groupSections.forEachIndexed { index, point ->
                                    val rectHeight =
                                        (point.value / groupTotalValue) * totalRectHeight
                                    val sumOfPrevHeights = prevHeights.sumOf { it }

                                    val rectOffsetY =
                                        (totalRectOffsetY + sumOfPrevHeights).toFloat()

                                    val prevPointColor = if (index > 0) {
                                        groupSections.getOrNull(index - 1)?.color
                                            ?: Color.Transparent
                                    } else Color.Transparent

                                    val topRadiusX = when {
                                        borderRadiusPx < totalRectHeight -> {
                                            when {
                                                (index > 0 && borderRadiusPx < rectHeight)
                                                        || index == 0 -> borderRadiusPx

                                                else -> 0.0f
                                            }
                                        }

                                        else -> 0.0f
                                    }

                                    drawRoundedTopRect(
                                        SolidColor(prevPointColor),
                                        Offset(rectOffsetX, rectOffsetY),
                                        Size(barThicknessPx, rectHeight.toFloat()),
                                    )

                                    drawRoundedTopRect(
                                        SolidColor(point.color),
                                        Offset(rectOffsetX, rectOffsetY),
                                        Size(barThicknessPx, rectHeight.toFloat()),
                                        CornerRadius(topRadiusX, topRadiusX),
                                    )

                                    prevHeights.add(rectHeight)
                                }
                            }
                        }
                    }
                }
            }

            // static y-axis canvas to scroll data against
            Canvas(
                modifier = Modifier
                    .width(rightCanvasWidth)
                    .fillMaxHeight()
                    .background(Color.White)
            ) {
                inset(
                    top = graphAreaInsetSize,
                    bottom = graphAreaInsetSize + xAxisLabelTextSize,
                    left = 0f,
                    right = 0f,
                ) {
                    // y-axis line
                    drawLine(
                        graphLineBrush,
                        Offset(0f, 0f),
                        Offset(0f, this.size.height),
                        lineThickness,
                    )

                    // graph start value
                    drawIntoCanvas {
                        it.nativeCanvas.drawText(
                            0.toString(),
                            labelTextPadding,
                            this.size.height,
                            yAxisLabelPaint,
                        )
                    }

                    // graph end value
                    drawIntoCanvas {
                        it.nativeCanvas.drawText(
                            dataMaxPointString,
                            labelTextPadding,
                            yAxisLabelTextSize / 1.5f,
                            yAxisLabelPaint,
                        )
                    }


                    val averageBarRectHeight = (dataAverage / dataMaximumPoint) * this.size.height
                    val averageLineOffsetY = (this.size.height - averageBarRectHeight).toFloat()
                    // average text
                    // todo extract into string resource
                    drawIntoCanvas {
                        it.nativeCanvas.drawText(
                            "avg",
                            labelTextPadding,
                            averageLineOffsetY,
                            averageLabelPaint,
                        )
                    }
                }
            }
        }
    }
}