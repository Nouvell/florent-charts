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
import io.nouvell.florentcharts.charts.bar.BarGraph
import io.nouvell.florentcharts.charts.bar.SubDividedBarGraph
import io.nouvell.florentcharts.model.Section
import io.nouvell.florentcharts.model.SubSection
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
                    BarGraphDemo()

                    Spacer(Modifier.height(20.dp))

                    SubDividedBarGraphDemo()
                }
            }
        }
    }
}

@Composable
private fun BarGraphDemo() {
    BarGraph(
        sections = listOf(
            Section(label = "S", value = 20_230.00),
            Section(label = "M", value = 6_530.00),
            Section(label = "T", value = 10_000.00),
            Section(label = "W", value = 2_230.00),
            Section(label = "T", value = 10_000.00),
            Section(label = "F", value = 14_000.00),
            Section(label = "S", value = 3_000.00),
        ),
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .height(170.dp),
    )
}

@Composable
private fun SubDividedBarGraphDemo() {
    SubDividedBarGraph(
        sections = listOf(
            Section(
                label = "M",
                subSections = listOf(
                    SubSection(234_343.0, Color(0xFF019F8E)),
                    SubSection(60_500.0, Color(0xFFFF2D55)),
                    SubSection(380_000.0, Color(0xFFFF5777)),
                    SubSection(100.0, Color(0xFF995A00)),
                    SubSection(54_300.0, Color(0xFF3395FF)),
                    SubSection(100_234.0, Color(0xFF7978DE)),
                    SubSection(93_222.0, Color(0xFF3DAE50)),
                ),
            ),
            Section(
                label = "T",
                subSections = listOf(
                    SubSection(234_343.0, Color(0xFFFFAA33)),
                    SubSection(60_500.0, Color(0xFFFCC003)),
                    SubSection(380_000.0, Color(0xFF9CABB8)),
                    SubSection(100.0, Color(0xFF7978DE)),
                    SubSection(380_000.0, Color(0xFFFF5777)),
                    SubSection(100.0, Color(0xFF995A00)),
                    SubSection(54_300.0, Color(0xFF3395FF)),
                ),
            ),
            Section(
                label = "W",
                subSections = listOf(
                    SubSection(380_000.0, Color(0xFFFF5777)),
                    SubSection(80_000.0, Color(0xFF9CABB8)),
                    SubSection(100.0, Color(0xFF7978DE)),
                ),
            ),
        ),
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .height(170.dp),
    )
}