package com.example.moviebox.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {

    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(0.5f) }

    LaunchedEffect(Unit) {

        launch {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(2000)
            )
        }

        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(2000)
        )

        delay(1000.milliseconds)

        onSplashFinished()
    }

    val goldenGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFD700),
            Color(0xFFFFB300),
            Color(0xFFB8860B)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(goldenGradient),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = "MovieBox",
            fontSize = 48.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            modifier = Modifier.graphicsLayer {
                this.alpha = alpha.value
                scaleX = scale.value
                scaleY = scale.value
            }
        )
    }
}