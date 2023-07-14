package io.nouvell.florentcharts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.nouvell.florentcharts.charts.bar.MultiColoredBarGraph
import io.nouvell.florentcharts.charts.bar.UniColorBarGraph
import io.nouvell.florentcharts.model.BarGraphDataPoint
import io.nouvell.florentcharts.model.BarGraphGroup
import io.nouvell.florentcharts.model.BarGraphSectionDataPoint
import io.nouvell.florentcharts.ui.theme.FlorentChartsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlorentChartsTheme {
                Column(
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    UniColorGraphDemo()

                    Spacer(Modifier.height(20.dp))

                    MultiColoredGraphDemo()
                }
            }
        }
    }
}

@Composable
private fun UniColorGraphDemo() {
    UniColorBarGraph(
        dataPoints = listOf(
            BarGraphDataPoint(
                label = "S",
                value = 20_230.00,
            ),
            BarGraphDataPoint(
                label = "M",
                value = 6_530.00,
            ),
            BarGraphDataPoint(
                label = "T",
                value = 10_000.00,
            ),
            BarGraphDataPoint(
                label = "W",
                value = 2_230.00,
            ),
            BarGraphDataPoint(
                label = "T",
                value = 10_000.00,
            ),
            BarGraphDataPoint(
                label = "F",
                value = 14_000.00,
            ),
            BarGraphDataPoint(
                label = "S",
                value = 3_000.00,
            ),
        ),
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .height(170.dp),
    )
}

@Composable
private fun MultiColoredGraphDemo() {
    MultiColoredBarGraph(
        dataPoints = listOf(
            BarGraphGroup(
                label = "S",
                sections = listOf(
                    BarGraphSectionDataPoint(234_343.0, Color(0xFF019F8E)),
                    BarGraphSectionDataPoint(60_500.0, Color(0xFFFF2D55)),
                    BarGraphSectionDataPoint(380_000.0, Color(0xFFFF5777)),
                    BarGraphSectionDataPoint(100.0, Color(0xFF995A00)),
                    BarGraphSectionDataPoint(54_300.0, Color(0xFF3395FF)),
                    BarGraphSectionDataPoint(100_234.0, Color(0xFF7978DE)),
                    BarGraphSectionDataPoint(93_222.0, Color(0xFF3DAE50)),
                ),
            ),
            BarGraphGroup(
                label = "T",
                sections = listOf(
                    BarGraphSectionDataPoint(234_343.0, Color(0xFFFFAA33)),
                    BarGraphSectionDataPoint(60_500.0, Color(0xFFFCC003)),
                    BarGraphSectionDataPoint(380_000.0, Color(0xFF9CABB8)),
                    BarGraphSectionDataPoint(100.0, Color(0xFF7978DE)),
                    BarGraphSectionDataPoint(380_000.0, Color(0xFFFF5777)),
                    BarGraphSectionDataPoint(100.0, Color(0xFF995A00)),
                    BarGraphSectionDataPoint(54_300.0, Color(0xFF3395FF)),
                ),
            ),
            BarGraphGroup(
                label = "W",
                sections = listOf(
                    BarGraphSectionDataPoint(380_000.0, Color(0xFFFF5777)),
                    BarGraphSectionDataPoint(80_000.0, Color(0xFF9CABB8)),
                    BarGraphSectionDataPoint(100.0, Color(0xFF7978DE)),
                ),
            ),
        ),
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .height(170.dp),
    )
}