package com.myproject.gymphysique.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.myproject.gymphysique.feature.home.homeNavigationRoute
import com.myproject.gymphysique.feature.home.homeScreen
import com.myproject.gymphysique.feature.home.navigateToHome
import com.myproject.gymphysique.feature.settings.navigation.navigateToSettings
import com.myproject.gymphysique.feature.settings.navigation.settingsScreen

@Composable
fun GymPhysiqueNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = homeNavigationRoute
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        homeScreen(onButtonClick = {navController.navigateToSettings()})
        settingsScreen(onButtonClick = { navController.navigateToHome() })
    }
}

