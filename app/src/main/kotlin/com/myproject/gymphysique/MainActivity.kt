package com.myproject.gymphysique

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.myproject.gymphysique.accountsetup.navigation.accountSetupNavigationRoute
import com.myproject.gymphysique.core.designsystem.theme.GymPhysiqueTheme
import com.myproject.gymphysique.feature.measure.measureNavigationRoute
import com.myproject.gymphysique.ui.GPApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState())
        var startDestination by mutableStateOf(accountSetupNavigationRoute)

        //Update uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.onEach {
                    uiState = it
                }.collect()
            }
        }

        // Keep the splash screen on-screen until the UI state is loaded. This condition is
        // evaluated each time the app needs to be redrawn so it should be fast to avoid blocking
        // the UI.
        splashScreen.setKeepOnScreenCondition {
            when (uiState.downloadState) {
                DownloadState.Loading -> true
                is DownloadState.Success -> {
                    val userData = (uiState.downloadState as DownloadState.Success).userData
                    if (userData.firstName.isNotBlank()){
                        startDestination = measureNavigationRoute
                    }
                    false
                }
            }
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            GymPhysiqueTheme() {
                GPApp()
            }
        }
    }
}
