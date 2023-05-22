package com.myproject.gymphysique

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.myproject.gymphysique.core.designsystem.theme.GymPhysiqueTheme
import com.myproject.gymphysique.navigation.GymPhysiqueNavHost
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
        if (!BuildConfig.BUILD_TYPE.contains("benchmark")){
            splashScreen.setKeepOnScreenCondition {
                when (uiState.downloadState) {
                    DownloadState.Loading -> true
                    DownloadState.Success -> false
                }
            }
        }

        //WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            uiState.startDestination?.let { startDestination ->
                GymPhysiqueTheme() {
                    GymPhysiqueNavHost(
                        navController = rememberNavController(),
                        startDestination = startDestination
                    )
                }
            }
        }

    }
}
