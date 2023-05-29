package com.myproject.gymphysique.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.myproject.gymphysique.accountsetup.navigation.accountSetupGraph
import com.myproject.gymphysique.ui.gpAppGraph
import com.myproject.gymphysique.ui.navigateToGp

@Composable
fun GymPhysiqueNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        gpAppGraph()
        accountSetupGraph(onNavigateToGpApp = { navController.navigateToGp() })
    }
}
