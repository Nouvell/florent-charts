package com.godwinaddy.florent.charts.bar

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.godwinaddy.florent.model.BarGraphDataPoint
import com.godwinaddy.florent.model.BarGraphGroup
import com.godwinaddy.florent.model.BarGraphSectionDataPoint
import io.nouvell.florentcharts.charts.bar.BarGraphContent

@Composable
fun UniColorBarGraph(
    dataPoints: List<BarGraphDataPoint>,
    modifier: Modifier = Modifier,
    barThickness: Dp = 13.dp,
    graphLineColor: Color = Color(0xFFE6EAED),
    labelTextColor: Color = Color(0xFF9CABB8),
    labelTextStyle: TextStyle = MaterialTheme.typography.caption,
    averageLineColor: Color = Color(0xFFCC2F26),
) {

    val graphDataGroups = remember(dataPoints) {
        dataPoints.map {
            BarGraphGroup(
                label = it.label,
                sections = listOf(
                    BarGraphSectionDataPoint(it.value, it.color)
                )
            )
        }
    }

    BarGraphContent(
        dataPoints = graphDataGroups,
        barThickness = barThickness,
        graphLineColor = graphLineColor,
        labelTextColor = labelTextColor,
        labelTextStyle = labelTextStyle,
        averageLineColor = averageLineColor,
        modifier = modifier,
    )

}