package com.myproject.gymphysique.feature.measure

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.myproject.gymphysique.feature.measure.ui.MeasureRoute

const val measureNavigationRoute = "measure_route"

fun NavController.navigateToMeasure(navOptions: NavOptions? = null) {
    this.navigate(measureNavigationRoute, navOptions)
}

fun NavGraphBuilder.measureGraph() {
    composable(route = measureNavigationRoute) {
        MeasureRoute()
    }
}
