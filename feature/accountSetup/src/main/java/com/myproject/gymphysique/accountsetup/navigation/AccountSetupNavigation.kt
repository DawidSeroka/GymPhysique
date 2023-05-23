package com.myproject.gymphysique.accountsetup.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.myproject.gymphysique.accountsetup.ui.AccountSetupRoute

@Suppress("TopLevelPropertyNaming")
const val accountSetupNavigationRoute = "account_setup_route"

fun NavGraphBuilder.accountSetupGraph(onNavigateToGpApp: () -> Unit) {
    composable(route = accountSetupNavigationRoute) {
        AccountSetupRoute(onNavigateToGpApp = onNavigateToGpApp)
    }
}
