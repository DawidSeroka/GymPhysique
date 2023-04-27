package com.myproject.gymphysique.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.myproject.gymphysique.feature.settings.SettingsRoute

const val settingsNavigationRoute = "settings_route"

fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    this.navigate(settingsNavigationRoute, navOptions)
}

fun NavGraphBuilder.settingsGraph(onButtonClick: () -> Unit) {
    composable(route = settingsNavigationRoute) {
        SettingsRoute(onClick = onButtonClick)
    }
}
