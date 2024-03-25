package org.m4xvel.weatherapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun DetailedWeatherScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        UpperNavigationView(navController = navController)
    }
}

@Composable
fun UpperNavigationView(navController: NavController) {
    TopAppBar(
        backgroundColor = Color.White,
        contentColor = Color.Black,
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        title = {
            Text(text = "Прогноз на 5 дней")
        }
    )
}
