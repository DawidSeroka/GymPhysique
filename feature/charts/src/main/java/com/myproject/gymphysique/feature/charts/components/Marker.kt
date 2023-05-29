package com.myproject.gymphysique.feature.charts.components

import android.graphics.Typeface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.myproject.gymphysique.feature.charts.components.Constants.GUIDELINE_ALPHA
import com.myproject.gymphysique.feature.charts.components.Constants.INDICATOR_CENTER_COMPONENT_SHADOW_RADIUS
import com.myproject.gymphysique.feature.charts.components.Constants.INDICATOR_OUTER_COMPONENT_ALPHA
import com.myproject.gymphysique.feature.charts.components.Constants.INDICATOR_SIZE_DP
import com.myproject.gymphysique.feature.charts.components.Constants.LABEL_BACKGROUND_SHADOW_DY
import com.myproject.gymphysique.feature.charts.components.Constants.LABEL_BACKGROUND_SHADOW_RADIUS
import com.myproject.gymphysique.feature.charts.components.Constants.LABEL_LINE_COUNT
import com.myproject.gymphysique.feature.charts.components.Constants.SHADOW_RADIUS_MULTIPLIER
import com.myproject.gymphysique.feature.charts.components.Constants.guidelineShape
import com.myproject.gymphysique.feature.charts.components.Constants.guidelineThickness
import com.myproject.gymphysique.feature.charts.components.Constants.indicatorCenterAndOuterComponentPaddingValue
import com.myproject.gymphysique.feature.charts.components.Constants.indicatorInnerAndCenterComponentPaddingValue
import com.myproject.gymphysique.feature.charts.components.Constants.labelBackgroundShape
import com.myproject.gymphysique.feature.charts.components.Constants.labelPadding
import com.patrykandpatrick.vico.compose.component.lineComponent
import com.patrykandpatrick.vico.compose.component.overlayingComponent
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.core.chart.insets.Insets
import com.patrykandpatrick.vico.core.chart.segment.SegmentProperties
import com.patrykandpatrick.vico.core.component.marker.MarkerComponent
import com.patrykandpatrick.vico.core.component.shape.DashedShape
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.cornered.Corner
import com.patrykandpatrick.vico.core.component.shape.cornered.MarkerCorneredShape
import com.patrykandpatrick.vico.core.context.MeasureContext
import com.patrykandpatrick.vico.core.extension.copyColor
import com.patrykandpatrick.vico.core.marker.Marker

/*
 * Copyright 2023 by Patryk Goworowski and Patrick Michalik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@Composable
internal fun rememberMarker(): Marker {
    val labelBackgroundColor = MaterialTheme.colorScheme.surface
    val labelBackground = remember(labelBackgroundColor) {
        ShapeComponent(labelBackgroundShape, labelBackgroundColor.toArgb()).setShadow(
            radius = LABEL_BACKGROUND_SHADOW_RADIUS,
            dy = LABEL_BACKGROUND_SHADOW_DY,
            applyElevationOverlay = true
        )
    }
    val label = textComponent(
        background = labelBackground,
        lineCount = LABEL_LINE_COUNT,
        padding = labelPadding,
        typeface = Typeface.MONOSPACE
    )
    val indicatorInnerComponent = shapeComponent(Shapes.pillShape, MaterialTheme.colorScheme.surface)
    val indicatorCenterComponent = shapeComponent(Shapes.pillShape, Color.White)
    val indicatorOuterComponent = shapeComponent(Shapes.pillShape, Color.White)
    val indicator = overlayingComponent(
        outer = indicatorOuterComponent,
        inner = overlayingComponent(
            outer = indicatorCenterComponent,
            inner = indicatorInnerComponent,
            innerPaddingAll = indicatorInnerAndCenterComponentPaddingValue
        ),
        innerPaddingAll = indicatorCenterAndOuterComponentPaddingValue
    )
    val guideline = lineComponent(
        MaterialTheme.colorScheme.onSurface.copy(GUIDELINE_ALPHA),
        guidelineThickness,
        guidelineShape
    )
    return remember(label, indicator, guideline) {
        object : MarkerComponent(label, indicator, guideline) {
            init {
                indicatorSizeDp = INDICATOR_SIZE_DP
                onApplyEntryColor = { entryColor ->
                    indicatorOuterComponent.color = entryColor.copyColor(INDICATOR_OUTER_COMPONENT_ALPHA)
                    with(indicatorCenterComponent) {
                        color = entryColor
                        setShadow(radius = INDICATOR_CENTER_COMPONENT_SHADOW_RADIUS, color = entryColor)
                    }
                }
            }

            override fun getInsets(context: MeasureContext, outInsets: Insets, segmentProperties: SegmentProperties) =
                with(context) {
                    outInsets.top = label.getHeight(context) + labelBackgroundShape.tickSizeDp.pixels +
                        LABEL_BACKGROUND_SHADOW_RADIUS.pixels * SHADOW_RADIUS_MULTIPLIER -
                        LABEL_BACKGROUND_SHADOW_DY.pixels
                }
        }
    }
}

@Suppress("TopLevelPropertyNaming")
private object Constants {

    const val LABEL_BACKGROUND_SHADOW_RADIUS = 4f
    const val LABEL_BACKGROUND_SHADOW_DY = 2f
    const val LABEL_LINE_COUNT = 1
    const val GUIDELINE_ALPHA = .2f
    const val INDICATOR_SIZE_DP = 36f
    const val INDICATOR_OUTER_COMPONENT_ALPHA = 32
    const val INDICATOR_CENTER_COMPONENT_SHADOW_RADIUS = 12f
    const val GUIDELINE_DASH_LENGTH_DP = 8f
    const val GUIDELINE_GAP_LENGTH_DP = 4f
    const val SHADOW_RADIUS_MULTIPLIER = 1.3f

    val labelBackgroundShape = MarkerCorneredShape(Corner.FullyRounded)
    val labelHorizontalPaddingValue = 8.dp
    val labelVerticalPaddingValue = 4.dp
    val labelPadding = dimensionsOf(labelHorizontalPaddingValue, labelVerticalPaddingValue)
    val indicatorInnerAndCenterComponentPaddingValue = 5.dp
    val indicatorCenterAndOuterComponentPaddingValue = 10.dp
    val guidelineThickness = 2.dp
    val guidelineShape = DashedShape(Shapes.pillShape, GUIDELINE_DASH_LENGTH_DP, GUIDELINE_GAP_LENGTH_DP)
}
