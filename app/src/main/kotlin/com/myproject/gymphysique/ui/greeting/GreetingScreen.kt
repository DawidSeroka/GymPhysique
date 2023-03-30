package com.myproject.gymphysique.ui.greeting

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.myproject.gymphysique.ui.common.AppScreen
import com.myproject.gymphysique.ui.common.theme.GymPhysiqueTheme

@Composable
fun GreetingScreen() {
    AppScreen {
        Greeting("Android")
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!", color = MaterialTheme.colorScheme.onBackground)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GymPhysiqueTheme { GreetingScreen() }
}
