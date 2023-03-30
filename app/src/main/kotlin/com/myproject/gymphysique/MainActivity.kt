package com.myproject.gymphysique

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.myproject.gymphysique.ui.common.GymPhysiqueNavHost
import com.myproject.gymphysique.ui.common.theme.GymPhysiqueTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent { GymPhysiqueTheme { GymPhysiqueNavHost() } }
    }
}
