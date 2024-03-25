package org.m4xvel.weatherapp.ui.animation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import org.koin.androidx.compose.koinViewModel
import org.m4xvel.weatherapp.ui.MainViewModel

@Composable
fun SwipeAnimation(
    mainViewModel: MainViewModel = koinViewModel()
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Asset("swipe.json"))
    val animSpec = LottieClipSpec.Progress(0f, 0.4f)
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 2,
        speed = 0.8f,
        clipSpec = animSpec,
        isPlaying = true
    )
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        LottieAnimation(
            composition = composition,
            { progress }
        )
    }
    if (progress == 0.4f) {
        mainViewModel.isPlayingAnimation(false)
    }
}