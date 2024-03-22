package org.m4xvel.weatherapp.ui.animation

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun SwipeAnimation(value: Boolean = false, navController: NavController) {
    val positionList = remember { mutableListOf<Double>() }
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Asset("swipe.json"))
    val animSpec = LottieClipSpec.Progress(0f, 0.4f)
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        speed = 0.8f,
        clipSpec = animSpec
    )
    Column(
        modifier = Modifier.fillMaxWidth()
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, _ ->
                    positionList.add(change.position.component1().toDouble())
                    if (positionList.first() - positionList.last() > 100) {
                        navController.navigate("DetailedWeatherScreen")
                    }
                }
            },
        horizontalAlignment = Alignment.End
    ) {
        if (value) {
            LottieAnimation(
                composition = composition,
                { progress }
            )
        }
    }
}