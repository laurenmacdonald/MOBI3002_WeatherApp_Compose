package com.example.mobi3002_weatherapp_compose.view

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import com.example.mobi3002_weatherapp_compose.R
import com.example.mobi3002_weatherapp_compose.model.WeatherForecastResponse
import com.example.mobi3002_weatherapp_compose.ui.theme.MOBI3002_WeatherApp_ComposeTheme
import com.example.mobi3002_weatherapp_compose.viewmodel.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    // Declaring variables to be used through out the program
    private lateinit var mainViewModel: MainViewModel
    private var lat: Double? = 0.0
    private var long: Double? = 0.0
    private var coord: String? = null

    private var loading: String? = null
    private var error: String? = null
    private val currentWeather = mutableMapOf<String, String>()
    private var forecastWeatherList = mutableListOf<MutableMap<String, String>>()

    // Variables specific for accessing permissions
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Instantiating the MainViewModel
        mainViewModel = MainViewModel()

        // Check to see if permissions are given to access the device's location
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Request the permission if not granted
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            } else {
                // Permission already granted
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            }
        }
        // Once permission granted, get the location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Get last known location
                location?.let {
                    // Get the lat long and concatenate it in a string to be used in the API params
                    lat = it.latitude
                    long = it.longitude
                    coord = "${lat}, $long"

                    // Get the weather data from the API using coord
                    mainViewModel.getWeatherData(coord)
                    mainViewModel.isLoading.observe(this) { isLoading -> // is sending the API request
                        // Set the result text to Loading
                        if (isLoading) loading = resources.getString(R.string.loading)
                    }
                    mainViewModel.isError.observe(this) { isError -> // Encountered an error in the process
                        // Hide display image and set result text to the error message
                        if (isError) {
                            error = mainViewModel.errorMessage
                        }
                    }
                    mainViewModel.weatherData.observe(this) { weatherData ->
                        getCurrentWeather(weatherData)
                        this.forecastWeatherList = getForecastWeather(weatherData)
                        setContent {
                            MOBI3002_WeatherApp_ComposeTheme {
                                Navigation(this.currentWeather, this.forecastWeatherList)
                            }
                        }
                    }

                }
            }
    }

    private fun getCurrentWeather(weatherData: WeatherForecastResponse) {

        weatherData.location?.let { location ->
            this.currentWeather["Name"] = location.name ?: ""
            this.currentWeather["Region"] = location.region ?: ""
            this.currentWeather["Country"] = location.country ?: ""
        }

        this.currentWeather["Date"] = weatherData.current?.lastUpdated ?: ""
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        // Parse the string to a Date object
        val dateOg: Date = currentWeather["Date"]?.let { dateFormat.parse(it) } ?: Date()
        // Define the desired output format
        val desiredDateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        // Format the Date object to the desired format
        currentWeather["Date"] = desiredDateFormat.format(dateOg).toString()

        weatherData.current?.let { forecast ->
            forecast.condition?.let { condition ->
                this.currentWeather["Condition"] = condition.text ?: ""
                this.currentWeather["Icon URL"] = ("https:" + condition.icon)
            }
            this.currentWeather["Feels Like"] = (forecast.feelslikeC ?: "").toString()
            this.currentWeather["Current Temp"] = (forecast.tempC ?: "").toString()
            this.currentWeather["Wind Speed"] = (forecast.windKph ?: "").toString()
            this.currentWeather["Wind Direction"] = forecast.windDir ?: ""
        }
        val keys: Set<String> = currentWeather.keys
        val values: MutableCollection<String> = currentWeather.values
        Log.d("Keys:", "$keys")
        Log.d("Values", "$values")
    }
}

fun getForecastWeather(weatherData: WeatherForecastResponse): MutableList<MutableMap<String, String>> {
    val forecastList = mutableListOf<MutableMap<String, String>>()
    val forecastListByDay = weatherData.forecast?.forecastday?.groupBy { it?.date }

    forecastListByDay?.forEach { (date, forecastItems) ->
        val forecastWeatherForDay = mutableMapOf<String, String>() // Create a new map for each day

        forecastWeatherForDay["Date"] = (date).toString()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        // Parse the string to a Date object
        val dateOg: Date = forecastWeatherForDay["Date"]?.let { dateFormat.parse(it) } ?: Date()
        // Define the desired output format
        val desiredDateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        // Format the Date object to the desired format
        forecastWeatherForDay["Date"] = desiredDateFormat.format(dateOg).toString()

        forecastItems.forEach { forecastItem ->
            forecastWeatherForDay["Max Temp"] = (forecastItem?.day?.maxtempC ?: "").toString()
            forecastWeatherForDay["Min Temp"] = (forecastItem?.day?.mintempC ?: "").toString()
            forecastWeatherForDay["Wind Max Kph"] = (forecastItem?.day?.maxwindKph ?: "").toString()
            forecastWeatherForDay["Humidity"] = (forecastItem?.day?.avghumidity ?: "").toString()
            forecastWeatherForDay["Condition"] =
                (forecastItem?.day?.condition?.text ?: "").toString()
            forecastWeatherForDay["Chance of rain"] =
                (forecastItem?.day?.dailyChanceOfRain ?: "").toString()
            forecastWeatherForDay["Chance of snow"] =
                (forecastItem?.day?.dailyChanceOfSnow ?: "").toString()
            forecastWeatherForDay["Icon URL"] =
                ("https:" + forecastItem?.day?.condition?.icon)
        }
        forecastList.add(forecastWeatherForDay) // Add each day's forecast to the forecastList
    }
    return forecastList
}