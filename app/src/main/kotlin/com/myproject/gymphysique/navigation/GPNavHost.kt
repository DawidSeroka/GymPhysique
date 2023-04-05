package com.myproject.gymphysique.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.myproject.gymphysique.feature.charts.navigation.chartsGraph
import com.myproject.gymphysique.feature.home.homeNavigationRoute
import com.myproject.gymphysique.feature.home.homeGraph
import com.myproject.gymphysique.feature.home.navigateToHome
import com.myproject.gymphysique.feature.settings.navigation.navigateToSettings
import com.myproject.gymphysique.feature.settings.navigation.settingsGraph

@Composable
fun GPNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = homeNavigationRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeGraph(onButtonClick = { navController.navigateToSettings() })
        settingsGraph(onButtonClick = { navController.navigateToHome() })
        chartsGraph(onButtonClick = {})
    }
}


