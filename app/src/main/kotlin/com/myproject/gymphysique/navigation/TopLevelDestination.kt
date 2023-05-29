package com.myproject.gymphysique.navigation

import com.myproject.gymphysique.core.designsystem.icon.GPIcons
import com.myproject.gymphysique.core.designsystem.icon.Icon
import com.myproject.gymphysique.core.designsystem.icon.Icon.DrawableResourceIcon
import com.myproject.gymphysique.core.designsystem.icon.Icon.ImageVectorIcon
import com.myproject.gymphysique.feature.measurements.R as measureR
import com.myproject.gymphysique.feature.settings.R as settingsR
import com.myproject.gymphysique.feature.charts.R as chartsR

enum class TopLevelDestination(
    val selectedIcon: Icon,
    val iconTextId: Int,
    val titleTextId: Int
) {
    MEASURE(
        selectedIcon = ImageVectorIcon(
            GPIcons.Measure
        ),
        iconTextId = measureR.string.measure,
        titleTextId = measureR.string.measure
    ),
    CHARTS(
        selectedIcon = ImageVectorIcon(
            GPIcons.Charts
        ),
        iconTextId = chartsR.string.charts,
        titleTextId = chartsR.string.charts
    ),
    SETTINGS(
        selectedIcon = DrawableResourceIcon(
            GPIcons.Settings
        ),
        iconTextId = settingsR.string.settings,
        titleTextId = settingsR.string.settings
    )
}
