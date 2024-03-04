package org.m4xvel.weatherapp.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.DefaultMarqueeIterations
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen() {
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Search()
            WeatherCard()
        }
    }
}

@Composable
private fun Search(mainViewModel: MainViewModel = koinViewModel()) {
    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 20.dp)
            .size(width = 600.dp, height = 60.dp),
        shape = RoundedCornerShape(30.dp),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        val searchText by mainViewModel.searchText
        val showButton by mainViewModel.showButton

        TextField(
            modifier = Modifier
                .fillMaxSize(),
            value = searchText,
            singleLine = true,
            onValueChange = { mainViewModel.setSearchText(it) },
            placeholder = { Text(text = "Найти город...") },
            textStyle = TextStyle(fontSize = 18.sp),
            colors = TextFieldDefaults.textFieldColors(
                disabledTextColor = Color.Transparent,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color.Gray
            ),
            leadingIcon = {
                IconButton(
                    onClick = { },
                    modifier = Modifier.padding(start = 5.dp)
                ) {
                    Icon(Icons.Rounded.Search, contentDescription = "search")
                }
            },
            trailingIcon = {
                if (showButton) {
                    IconButton(
                        onClick = {
                            mainViewModel.clearSearchText()
                        },
                        modifier = Modifier.padding(end = 5.dp),
                    ) {
                        Icon(Icons.Rounded.Close, contentDescription = "close")
                    }
                }
            }
        )
    }
}

@Composable
private fun WeatherCard(mainViewModel: MainViewModel = koinViewModel()) {

    val city by mainViewModel.city
    val temp by mainViewModel.temp
    val speed by mainViewModel.speed
    val humidity by mainViewModel.humidity
    val pressure by mainViewModel.pressure

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .size(width = 600.dp, height = 250.dp)
                .padding(horizontal = 10.dp),
            shape = RoundedCornerShape(30.dp),
            border = BorderStroke(1.dp, Color.Black),
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                CityAndTemperature(city, temp)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 30.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    AdditionalDoubleInformation("скорость ветра:", speed, "м/c")
                    AdditionalIntInformation("влажность:", humidity, "%")
                    AdditionalIntInformation("давление:", pressure, "mmHg")
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CityAndTemperature(city: String, temperature: Int) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .width(220.dp)
                .basicMarquee(
                    animationMode = MarqueeAnimationMode.Immediately,
                    spacing = MarqueeSpacing(50.dp)
            ),
            text = "$city",
            fontSize = 48.sp,
        )
        Text(
            "$temperature°",
            fontSize = 64.sp
        )
    }
}

@Composable
private fun AdditionalDoubleInformation(optionName: String, value: Double, symbol: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$optionName \t ${value}${symbol}")
    }
}

@Composable
private fun AdditionalIntInformation(optionName: String, value: Int, symbol: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$optionName \t ${value}${symbol}")
    }
}