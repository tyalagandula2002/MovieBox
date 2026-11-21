package com.example.moviebox.navigation

import androidx.compose.runtime.*
import com.example.moviebox.ui.screens.SplashScreen
import com.example.moviebox.ui.screens.HomeScreen

@Composable
fun MovieBoxNavigation() {

    var currentScreen by remember {
        mutableStateOf("splash")
    }

    when (currentScreen) {

        "splash" -> {
            SplashScreen(
                onSplashFinished = {
                    currentScreen = "home"
                }
            )
        }

        "home" -> {
            HomeScreen()
        }
    }
}