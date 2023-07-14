package io.nouvell.florentcharts.charts.bar

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.nouvell.florentcharts.model.BarGraphGroup
import io.nouvell.florentcharts.charts.bar.BarGraphContent

@Composable
fun MultiColoredBarGraph(
    dataPoints: List<BarGraphGroup>,
    modifier: Modifier = Modifier,
    graphLineColor: Color = Color(0xFFE6EAED),
    labelTextColor: Color = Color(0xFF9CABB8),
    labelTextStyle: TextStyle = MaterialTheme.typography.caption,
    averageLineColor: Color = Color(0xFFCC2F26),
    barThickness: Dp = 13.dp,
) {
    BarGraphContent(
        dataPoints = dataPoints,
        barThickness = barThickness,
        graphLineColor = graphLineColor,
        labelTextColor = labelTextColor,
        labelTextStyle = labelTextStyle,
        averageLineColor = averageLineColor,
        modifier = modifier,
    )
}