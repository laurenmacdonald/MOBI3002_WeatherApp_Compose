package com.example.mobi3002_weatherapp_compose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobi3002_weatherapp_compose.model.WeatherForecastResponse
import com.example.mobi3002_weatherapp_compose.networking.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

class MainViewModel(): ViewModel() {
    /* Declaring MutableLiveData and LiveData local variables. MutableLiveData used to
    hold the response from the API and LiveData takes the value of the MutableLiveData.
     */
    private val _weatherData = MutableLiveData<WeatherForecastResponse>()
    val weatherData: LiveData<WeatherForecastResponse> get() = _weatherData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    var errorMessage: String = ""
        private set         // private setter - can only be used within this class.


    /**
     * Function to get the weather data from the API, save it in _weatherData
     */
    fun getWeatherData(coord: String?) {
        _isLoading.value = true
        _isError.value = false

        val client = ApiConfig.getApiService().getCurrentWeather(coord = coord)

        // Send API request using Retrofit
        client.enqueue(object : Callback<WeatherForecastResponse> {
            override fun onResponse(
                call: Call<WeatherForecastResponse>,
                response: Response<WeatherForecastResponse>
            ) {
                val responseBody = response.body()
                if(!response.isSuccessful || responseBody == null) {
                    onError("Data Processing Error")
                    return
                }
                _isLoading.value = false
                _weatherData.postValue(responseBody)
            }

            override fun onFailure(call: Call<WeatherForecastResponse>, t: Throwable) {
                onError(t.message)
                t.printStackTrace()
            }
        })
    }

    /**
     * Function to display error message if the API request is not successful
     */
    private fun onError(inputMessage: String?) {
        val message = if (inputMessage.isNullOrBlank() or inputMessage.isNullOrEmpty()) "Unknown error"
        else inputMessage

        errorMessage = StringBuilder("ERROR: ")
            .append("$message some data may not display properly").toString()

        _isError.value = true
        _isLoading.value = false
    }

}