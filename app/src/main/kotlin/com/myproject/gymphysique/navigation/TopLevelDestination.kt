package com.myproject.gymphysique.navigation

import com.myproject.gymphysique.core.designsystem.icon.GPIcons
import com.myproject.gymphysique.core.designsystem.icon.Icon
import com.myproject.gymphysique.core.designsystem.icon.Icon.DrawableResourceIcon
import com.myproject.gymphysique.core.designsystem.icon.Icon.ImageVectorIcon
import com.myproject.gymphysique.feature.measurements.R as homeR
import com.myproject.gymphysique.feature.settings.R as settingsR

enum class TopLevelDestination(
    val selectedIcon: Icon,
    val iconTextId: Int,
    val titleTextId: Int
) {
    HOME(
        selectedIcon = DrawableResourceIcon(
            GPIcons.Home
        ),
        iconTextId = homeR.string.home,
        titleTextId = homeR.string.home
    ),
    SETTINGS(
        selectedIcon = DrawableResourceIcon(
            GPIcons.Settings
        ),
        iconTextId = settingsR.string.settings,
        titleTextId = settingsR.string.settings
    )
}