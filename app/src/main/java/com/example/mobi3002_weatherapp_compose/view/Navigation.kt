package com.example.mobi3002_weatherapp_compose.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

/**
 * Navigation to route through the app
 */
@Composable
fun Navigation(
    currentWeather: Map<String, String>,
    forecastWeatherList: List<MutableMap<String, String>>
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            ForecastBottomNavigation(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.CurrentScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screen.CurrentScreen.route) {
                CurrentScreen(currentWeather)
            }
            composable(route = Screen.ForecastScreen.route) {
                Column {
                    ForecastScreen(modifier = Modifier, forecastWeatherList)
                }
            }
        }
    }
}

@Composable
private fun ForecastBottomNavigation(navController: NavController, modifier: Modifier = Modifier) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondary,
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = if (navController.currentDestination?.route == Screen.CurrentScreen.route) {
                        Icons.Filled.WbSunny // Filled icon when selected
                    } else {
                        Icons.Outlined.WbSunny // Outline icon when not selected
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            label = {
                Text(
                    text = "Current",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            },
            selected = navController.currentDestination?.route == Screen.CurrentScreen.route,
            onClick = { navController.navigate(Screen.CurrentScreen.route) }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = if (navController.currentDestination?.route == Screen.ForecastScreen.route) {
                        Icons.Filled.DateRange
                    } else {
                        Icons.Outlined.DateRange
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            label = {
                Text(
                    text = "3 Day Forecast",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            },
            selected = navController.currentDestination?.route == Screen.ForecastScreen.route,
            onClick = { navController.navigate(Screen.ForecastScreen.route) }
        )
    }
}

@Composable
fun CurrentScreen(currentWeather: Map<String, String>) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(
                    text = "Current Weather",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Row {
                currentWeather["Name"]?.let {
                    Text(
                        text = "$it, ",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.ExtraBold
                        ), modifier = Modifier.padding(start = 1.dp)
                    )
                }
                currentWeather["Region"]?.let {
                    Text(
                        text = it, style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.ExtraBold
                        ), modifier = Modifier.padding(start = 1.dp)
                    )
                }
            }
            currentWeather["Date"]?.let {
                Text(text = it, style = MaterialTheme.typography.bodyLarge)
            }
            Row(
                modifier = Modifier.padding(4.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp),
                    model = currentWeather["Icon URL"],
                    contentDescription = currentWeather["Condition"]
                )
            }
            Row(
                modifier = Modifier.padding(4.dp)
            ) {
                currentWeather["Condition"]?.let {
                    Text(text = it, style = MaterialTheme.typography.headlineSmall)
                }
            }
            Row(
                modifier = Modifier.padding(4.dp)
            ) {
                currentWeather["Current Temp"]?.let {
                    Text(text = "$it°C", style = MaterialTheme.typography.headlineLarge)
                }
            }
            Row(
                modifier = Modifier.padding(4.dp)
            ) {
                currentWeather["Feels Like"]?.let {
                    Text(text = "Feels like $it°C", style = MaterialTheme.typography.bodyLarge)
                }
            }
            Row(
                modifier = Modifier.padding(4.dp)
            ) {
                currentWeather["Wind Direction"]?.let {
                    Text(text = "Wind: $it", style = MaterialTheme.typography.bodyLarge)
                }
                currentWeather["Wind Speed"]?.let {
                    Text(
                        text = "$it kph",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 1.dp)
                    )
                }
            }
        }

    }
}


@Composable
fun ForecastScreen(modifier: Modifier, forecastWeatherList: List<MutableMap<String, String>>) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(
                    text = "3 Day Forecast",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Row {
                CreateCards(modifier, forecastWeatherList)
            }
        }
    }

}

@Composable
private fun CreateCards(
    modifier: Modifier = Modifier,
    forecastWeatherList: List<MutableMap<String, String>>
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = forecastWeatherList) { forecastWeather ->
            PopulateCard(forecastWeather)
        }
    }
}


@Composable
private fun PopulateCard(forecastWeather: MutableMap<String, String>) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ), modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(forecastWeather)
    }
}

@Composable
private fun CardContent(forecastWeather: MutableMap<String, String>) {
    Row(
        modifier = Modifier.padding(12.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            forecastWeather["Date"]?.let {
                Text(
                    text = it, style = MaterialTheme.typography.titleSmall
                )
            }
            Row(
                modifier = Modifier.padding(4.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .height(62.dp)
                        .width(62.dp)
                        .padding(end = 8.dp),
                    model = forecastWeather["Icon URL"],
                    contentDescription = forecastWeather["Condition"]
                )
                Column {
                    Row {
                        forecastWeather["Max Temp"]?.let {
                            Text(
                                text = "$it°C High",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.ExtraBold
                                )
                            )
                        }
                        forecastWeather["Min Temp"]?.let {
                            Text(
                                text = "$it°C Low", style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.ExtraBold
                                ), modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                    Row {
                        forecastWeather["Condition"]?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium.copy(),
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                        if (!forecastWeather["Chance of rain"].equals("0")) {
                            forecastWeather["Chance of rain"]?.let {
                                Text(
                                    text = "$it%",
                                    style = MaterialTheme.typography.bodyMedium.copy(),
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                        .padding(start = 16.dp)
                                )
                            }
                        } else if (!forecastWeather["Chance of snow"].equals("0")) {
                            forecastWeather["Chance of snow"]?.let {
                                Text(
                                    text = "$it%",
                                    style = MaterialTheme.typography.bodyMedium.copy(),
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                        .padding(start = 16.dp)
                                )
                            }
                        }
                    }

                    Row {
                        forecastWeather["Humidity"]?.let {
                            Text(
                                text = "Humidity $it%",
                                style = MaterialTheme.typography.bodyMedium.copy(),
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                        forecastWeather["Wind Max Kph"]?.let {
                            Text(
                                text = "Max winds $it kph",
                                style = MaterialTheme.typography.bodyMedium.copy(),
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .padding(start = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}