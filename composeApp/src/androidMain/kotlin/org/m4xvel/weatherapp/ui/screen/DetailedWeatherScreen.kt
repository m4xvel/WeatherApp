package org.m4xvel.weatherapp.ui.screen

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.m4xvel.weatherapp.domain.model.Weather
import org.m4xvel.weatherapp.ui.MainViewModel
import kotlin.math.roundToInt

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
                    text = "${mainViewModel.getDayOfMonth(iteration)} ${
                        mainViewModel.getDayOfWeek(
                            iteration
                        )
                    }",
                    color = textColor,
                    modifier = Modifier.align(alignment = Alignment.Center)
                )
            }
        }
    }
    HorizontalPager(
        state = pagerState
    ) { page ->
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Forecast(mainViewModel = mainViewModel, page = page, text = "Температура") { it.temp.roundToInt() }
            Forecast(mainViewModel = mainViewModel, page = page, text = "Скорость ветра") { it.speed }
            Forecast(mainViewModel = mainViewModel, page = page, text = "Влажность") { it.humidity }
            Forecast(mainViewModel = mainViewModel, page = page, text = "Давление") { it.pressure }
        }
    }
}

@Composable
private fun Forecast(mainViewModel: MainViewModel, page: Int, text: String, value: (Weather) -> Any) {
    Column(modifier = Modifier.padding(top = 30.dp)) {
        Text(text = text, fontStyle = FontStyle.Italic, modifier = Modifier.padding(5.dp))
        Divider(
            modifier = Modifier.fillMaxWidth()
                .height(1.dp),
            color = Color.Black
        )
        Row(
            modifier = Modifier.fillMaxWidth()
                .height(80.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ForecastData(
                mainViewModel = mainViewModel,
                page = page,
                text = "Ночь",
                time = 0
            ) {
                value(it)
            }
            ForecastData(
                mainViewModel = mainViewModel,
                page = page,
                text = "Утро",
                time = 6
            ) {
                value(it)
            }
            ForecastData(
                mainViewModel = mainViewModel,
                page = page,
                text = "День",
                time = 12
            ) {
                value(it)
            }
            ForecastData(
                mainViewModel = mainViewModel,
                page = page,
                text = "Вечер",
                time = 18
            ) {
                value(it)
            }
        }
        Divider(
            color = Color.Black,
            modifier = Modifier.height(1.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun ForecastData(mainViewModel: MainViewModel, page: Int, text: String, time: Int, value: (Weather) -> Any) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text)
        Text(text = mainViewModel.getDayOfMonthForecast(page)
            ?.filter { it.key == time }
            ?.flatMap { it.value }
            ?.map { value(it) }
            ?.joinToString("") ?: ""
        )
    }
}
