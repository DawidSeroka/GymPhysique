package com.myproject.gymphysique.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.myproject.gymphysique.feature.settings.ui.SettingsRoute

@Suppress("TopLevelPropertyNaming")
const val settingsNavigationRoute = "settings_route"

fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    this.navigate(settingsNavigationRoute, navOptions)
}

fun NavGraphBuilder.settingsGraph() {
    composable(route = settingsNavigationRoute) {
        SettingsRoute()
    }
}
