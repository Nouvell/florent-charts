package io.nouvell.florentcharts.model

import androidx.compose.ui.graphics.Color


/**
 * Represents a single category/bar that has one more subsections.
 * For example, in the diagram below, Sales with [SubSection] A and B is one section
 * and Expenses is another.
 *
 * Y
 * ▲
 * │   ┌─────┐
 * │   │  A  │
 * │   ├─────┤
 * │   │  B  │   ┌─────┐
 * │   │     │   │  C  │
 * └───┴─────┴───┴─────┴─────► X
 *     Sales      Expenses
 */
data class Section(
    val label: String,
    val subSections: List<SubSection>
) {
    constructor(
        label: String,
        value: Double,
        color: Color = Color(0xFF15996A)
    ) : this(label, listOf(SubSection(value, color)))

    val totalValue: Double = subSections.sumOf { it.value }
}


/**
 * Represents a portion of a [Section] on a sub-divided bar graph.
 * For example, in the diagram below, the parts of the bar labelled
 * A, B and C are all subsections of the Sales [Section].
 *
 * Y
 * ▲
 * │
 * │    ┌─────┐
 * │    │  A  │
 * │    ├─────┤
 * │    │  B  │
 * │    ├─────┤
 * │    │  C  │
 * └────┴─────┴────────► X
 *       Sales
 */
data class SubSection(
    val value: Double,
    val color: Color = Color(0xFF15996A),
)
