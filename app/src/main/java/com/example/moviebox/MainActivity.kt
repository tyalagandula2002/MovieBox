package com.example.moviebox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.moviebox.navigation.MovieBoxNavigation
import com.example.moviebox.ui.theme.MovieBoxTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            MovieBoxTheme {

                MovieBoxNavigation()

            }
        }
    }
}