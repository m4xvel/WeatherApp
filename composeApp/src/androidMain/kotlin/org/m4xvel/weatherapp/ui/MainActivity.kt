package org.m4xvel.weatherapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.m4xvel.weatherapp.ui.screen.DetailedWeatherScreen
import org.m4xvel.weatherapp.ui.screen.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val timeEnter = 700
            val timeExit = 1200
            MaterialTheme {
                NavHost(
                    navController = navController,
                    startDestination = "HomeScreen",
                    enterTransition = {
                        fadeIn(tween(timeEnter)) + slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(timeEnter)
                        )
                    },
                    exitTransition = {
                        fadeOut(tween(timeEnter)) + slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(timeEnter)
                        )
                    },
                    popEnterTransition = {
                        fadeIn(tween(timeExit)) + slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(timeExit)
                        )
                    },
                    popExitTransition = {
                        fadeOut(tween(timeExit)) + slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(timeExit)
                        )
                    }
                ) {
                    composable("HomeScreen") {
                        HomeScreen(navController = navController)
                    }
                    composable("DetailedWeatherScreen") {
                        DetailedWeatherScreen()
                    }
                }
            }
        }
    }
}