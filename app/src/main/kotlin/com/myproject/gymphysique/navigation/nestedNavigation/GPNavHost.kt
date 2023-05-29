package com.myproject.gymphysique.navigation.nestedNavigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.myproject.gymphysique.feature.charts.navigation.chartsGraph
import com.myproject.gymphysique.feature.measure.navigation.measureNavigationRoute
import com.myproject.gymphysique.feature.measure.navigation.measureGraph
import com.myproject.gymphysique.feature.settings.navigation.settingsGraph

@Composable
fun GPNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = measureNavigationRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        measureGraph()
        settingsGraph()
        chartsGraph()

    }
}


