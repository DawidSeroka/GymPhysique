package com.myproject.gymphysique.ui

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember


@Composable
fun GPApp(

){
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        bottomBar = {

        }
    ) { padding ->
    }
}

@Composable
private fun GPBottomBar(
    destination: List<>
)