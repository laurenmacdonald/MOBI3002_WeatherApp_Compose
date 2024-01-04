package com.example.mobi3002_weatherapp_compose.networking

import com.example.mobi3002_weatherapp_compose.model.WeatherForecastResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Using Retrofit library to retrieve data from the weather API. The Call Retrofit method is invoked
 * to send a request to the server and return the response.
 */
interface ApiService {
    @GET("forecast.json")
    fun getCurrentWeather(
        @Query("key") key: String = ApiConfig.API_KEY,
        @Query("q") coord: String?,
        @Query("days") days: String = "7",
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ): Call<WeatherForecastResponse>
}