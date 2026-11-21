
package com.example.moviebox.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.moviebox.data.UserReview
import com.example.moviebox.ui.screens.HomeScreen
import com.example.moviebox.ui.screens.ReviewScreen
import com.example.moviebox.ui.screens.SplashScreen

@Composable
fun MovieBoxNavigation() {

    var currentScreen by rememberSaveable {
        mutableStateOf("splash")
    }

    var requestedMovie by remember {
        mutableStateOf<String?>(null)
    }

    val reviews = remember {
        mutableStateListOf<UserReview>()
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
            HomeScreen(
                onReviewsClick = {
                    requestedMovie = null
                    currentScreen = "reviews"
                },
                onWriteReview = { movie ->
                    requestedMovie = movie.title
                    currentScreen = "reviews"
                }
            )
        }

        "reviews" -> {
            ReviewScreen(
                reviews = reviews,
                initialMovieTitle = requestedMovie,
                onBack = {
                    currentScreen = "home"
                }
            )
        }
    }
}
