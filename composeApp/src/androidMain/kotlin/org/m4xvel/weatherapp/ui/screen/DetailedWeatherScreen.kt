package org.m4xvel.weatherapp.ui.screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import org.m4xvel.weatherapp.ui.MainViewModel

@Composable
fun DetailedWeatherScreen(navController: NavController, mainViewModel: MainViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        UpperNavigationView(navController = navController)
        HorizontalPagerView(mainViewModel = mainViewModel)
    }
}

@Composable
private fun UpperNavigationView(navController: NavController) {
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HorizontalPagerView(mainViewModel: MainViewModel) {
    val pagerState = rememberPagerState(pageCount = { 5 })
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        var color: Color
        var textColor: Color
        var widthBorder: Int
        repeat(pagerState.pageCount) { iteration ->
            if (pagerState.currentPage == iteration) {
                color = Color.Black
                textColor = Color.White
                widthBorder = 0
            } else {
                color = Color.White
                textColor = Color.Black
                widthBorder = 1
            }
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(14.dp))
                    .background(color)
                    .size(width = 64.dp, height = 28.dp)
                    .border(
                        width = widthBorder.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(14.dp)
                    )
                    .clickable {
                        mainViewModel.toPage(pagerState, iteration)
                    },
            ) {
                Text(
                    text = "${ mainViewModel.getDayOfMonth(iteration) } ${ mainViewModel.getDayOfWeek(iteration) }",
                    color = textColor,
                    modifier = Modifier.align(alignment = Alignment.Center)
                )
            }
        }
    }
    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState
    ) { page ->
        Text(
            text = "${ mainViewModel.getDayOfMonthForecast(page) }"
        )
    }
}
