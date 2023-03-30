package com.myproject.gymphysique.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.myproject.gymphysique.ui.greeting.greetingGraph
import com.myproject.gymphysique.ui.greeting.greetingRoute

@Composable
fun GymPhysiqueNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = greetingRoute,
    ) {
        greetingGraph()
    }
}

