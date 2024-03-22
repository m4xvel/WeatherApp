package org.m4xvel.weatherapp.ui.animation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun MonkeyAnimation(value: Boolean) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Asset("monkey.json"))
    Column(modifier = Modifier.fillMaxSize()) {
        if (value) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever
            )
        }
    }
}