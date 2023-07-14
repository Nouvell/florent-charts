package io.nouvell.florentcharts.model

import androidx.compose.ui.graphics.Color

data class BarGraphDataPoint(
    val label: String,
    val value: Double,
    val color: Color = Color(0xFF15996A),
)

data class BarGraphGroup(
    val label: String,
    val sections: List<BarGraphSectionDataPoint>
) {

    val totalValue: Double = sections.sumOf { it.value }
}

data class BarGraphSectionDataPoint(
    val value: Double,
    val color: Color = Color(0xFF15996A),
)
