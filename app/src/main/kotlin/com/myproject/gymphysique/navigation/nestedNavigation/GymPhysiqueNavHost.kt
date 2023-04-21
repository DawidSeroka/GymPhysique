package com.myproject.gymphysique.navigation.nestedNavigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.myproject.gymphysique.accountsetup.navigation.accountSetupGraph
import com.myproject.gymphysique.feature.measure.measureNavigationRoute
import com.myproject.gymphysique.ui.gpAppGraph

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
        accountSetupGraph()
    }
}