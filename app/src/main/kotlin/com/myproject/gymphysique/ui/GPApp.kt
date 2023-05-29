package com.myproject.gymphysique.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.myproject.gymphysique.core.designsystem.component.GPNavigationBar
import com.myproject.gymphysique.core.designsystem.component.GpNavigationBarItem
import com.myproject.gymphysique.core.designsystem.icon.Icon.DrawableResourceIcon
import com.myproject.gymphysique.core.designsystem.icon.Icon.ImageVectorIcon
import com.myproject.gymphysique.core.designsystem.theme.GymPhysiqueTheme
import com.myproject.gymphysique.navigation.TopLevelDestination
import com.myproject.gymphysique.navigation.nestedNavigation.GPNavHost
import timber.log.Timber

@Suppress("TopLevelPropertyNaming")
const val gpAppNavigationRoute = "gp_app_route"

fun NavController.navigateToGp(navOptions: NavOptions? = null) {
    this.navigate(gpAppNavigationRoute, navOptions)
}

fun NavGraphBuilder.gpAppGraph() {
    composable(route = gpAppNavigationRoute) {
        GPApp()
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun GPApp(
    appState: GPAppState = rememberGpAppState()
) {
    val context = LocalContext.current
    val bottomWindowsInsets = WindowInsets.systemBars.getBottom(Density(context))
    Scaffold(
        bottomBar = {
            GPBottomBar(
                destinations = appState.topLevelDestinations,
                onNavigateToDestination = appState::navigateToTopLevelDestination,
                currentDestination = appState.currentDestination,
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                    //WorkAround for issue related with system paddings
                .padding(
                    bottom = if (bottomWindowsInsets > 0) innerPadding.calculateBottomPadding() - (bottomWindowsInsets / 2).dp
                    else
                        innerPadding.calculateBottomPadding()
                ).also {
                    Timber.d(
                        "Padding values gpApp = $innerPadding," +
                                "WindowsInsets = ${bottomWindowsInsets}"
                    )
                }
        ) {
            //TODO() Temporary workAround for wrong paddingValues
            GPNavHost(
                modifier = Modifier,
                navController = appState.navController
            )
        }
    }
}

@Composable
private fun GPBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?
) {
    GPNavigationBar {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            GpNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    when (val icon = destination.selectedIcon) {
                        is DrawableResourceIcon -> Icon(
                            painter = painterResource(id = icon.id),
                            contentDescription = null
                        )
                        is ImageVectorIcon -> Icon(
                            imageVector = icon.imageVector,
                            contentDescription = null
                        )
                    }
                },
                label = { Text(stringResource(id = destination.iconTextId)) }
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false

@Preview(name = "Light mode")
@Preview(
    name = "Dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun GPAppPreview() {
    GymPhysiqueTheme {
        GPApp()
    }
}
