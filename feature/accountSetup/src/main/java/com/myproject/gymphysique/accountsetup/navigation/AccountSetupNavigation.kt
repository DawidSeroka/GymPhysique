package com.myproject.gymphysique.accountsetup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.myproject.gymphysique.accountsetup.AccountSetupRoute

const val accountSetupNavigationRoute = "account_setup_route"

fun NavController.navigateToAccountSetup(navOptions: NavOptions? = null){
    this.navigate(accountSetupNavigationRoute, navOptions)
}

fun NavGraphBuilder.measureGraph(){
    composable(route = accountSetupNavigationRoute){
        AccountSetupRoute()
    }
}