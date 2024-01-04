package com.example.mobi3002_weatherapp_compose.view

sealed class Screen (val route: String) {
    object ForecastScreen : Screen("forecast_screen")
    object CurrentScreen : Screen("current_screen")
}

