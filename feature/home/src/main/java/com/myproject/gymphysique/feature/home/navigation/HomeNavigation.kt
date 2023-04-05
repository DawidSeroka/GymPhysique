package com.myproject.gymphysique.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val homeNavigationRoute = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null){
    this.navigate(homeNavigationRoute, navOptions)
}

fun NavGraphBuilder.homeGraph(onButtonClick: () -> Unit){
    composable(route = homeNavigationRoute){
        HomeRoute(onClick = onButtonClick)
    }
}