package com.myproject.gymphysique.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.util.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.myproject.gymphysique.feature.charts.navigation.navigateToCharts
import com.myproject.gymphysique.feature.measure.measureNavigationRoute
import com.myproject.gymphysique.feature.measure.navigateToMeasure
import com.myproject.gymphysique.feature.settings.navigation.navigateToSettings
import com.myproject.gymphysique.feature.settings.navigation.settingsNavigationRoute
import com.myproject.gymphysique.navigation.TopLevelDestination

@Composable
fun rememberGpAppState(
    navController: NavHostController = rememberNavController()
): GPAppState {
    return remember(navController) {
        GPAppState(navController = navController)
    }
}

@Stable
class GPAppState(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            measureNavigationRoute -> TopLevelDestination.MEASURE
            settingsNavigationRoute -> TopLevelDestination.SETTINGS
            else -> null
        }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }

            when (topLevelDestination) {
                TopLevelDestination.MEASURE -> navController.navigateToMeasure(topLevelNavOptions)
                TopLevelDestination.SETTINGS -> navController.navigateToSettings(topLevelNavOptions)
                TopLevelDestination.CHARTS -> navController.navigateToCharts(topLevelNavOptions)
            }
        }
    }
}