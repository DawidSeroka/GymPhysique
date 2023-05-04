package com.myproject.gymphysique.feature.charts.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.myproject.gymphysique.feature.charts.ui.ChartsRoute

const val chartsNavigationRoute = "charts_route"

fun NavController.navigateToCharts(navOptions: NavOptions? = null) {
    this.navigate(chartsNavigationRoute, navOptions)
}

fun NavGraphBuilder.chartsGraph() {
    composable(route = chartsNavigationRoute) {
        ChartsRoute()
    }
}
