package com.example.mobi3002_weatherapp_compose.model

import com.google.gson.annotations.SerializedName

/**
 * POJO Class created via the RoboPOJOGenerator plugin by supplying it with the JSON output
 * from the API call to get the weather forecast. Uses GSON for data deserialization (reconstructing
 * an object from the serialized JSON).
 */

data class WeatherForecastResponse(

    @field:SerializedName("current")
    val current: Current? = null,

    @field:SerializedName("location")
    val location: Location? = null,

    @field:SerializedName("forecast")
    val forecast: Forecast? = null
)

data class Location(

    @field:SerializedName("localtime")
    val localtime: String? = null,

    @field:SerializedName("country")
    val country: String? = null,

    @field:SerializedName("localtime_epoch")
    val localtimeEpoch: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("lon")
    val lon: Any? = null,

    @field:SerializedName("region")
    val region: String? = null,

    @field:SerializedName("lat")
    val lat: Any? = null,

    @field:SerializedName("tz_id")
    val tzId: String? = null
)

data class Astro(

    @field:SerializedName("moonset")
    val moonset: String? = null,

    @field:SerializedName("moon_illumination")
    val moonIllumination: Int? = null,

    @field:SerializedName("sunrise")
    val sunrise: String? = null,

    @field:SerializedName("moon_phase")
    val moonPhase: String? = null,

    @field:SerializedName("sunset")
    val sunset: String? = null,

    @field:SerializedName("is_moon_up")
    val isMoonUp: Int? = null,

    @field:SerializedName("is_sun_up")
    val isSunUp: Int? = null,

    @field:SerializedName("moonrise")
    val moonrise: String? = null
)

data class ForecastdayItem(

    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("astro")
    val astro: Astro? = null,

    @field:SerializedName("day")
    val day: Day? = null
)


data class Forecast(

    @field:SerializedName("forecastday")
    val forecastday: List<ForecastdayItem?>? = null
)

data class Condition(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("icon")
    val icon: String? = null,

    @field:SerializedName("text")
    val text: String? = null
)

data class Day(

    @field:SerializedName("avgvis_km")
    val avgvisKm: Any? = null,

    @field:SerializedName("avgtemp_c")
    val avgtempC: Any? = null,

    @field:SerializedName("daily_chance_of_snow")
    val dailyChanceOfSnow: Int? = null,

    @field:SerializedName("maxtemp_c")
    val maxtempC: Any? = null,

    @field:SerializedName("mintemp_c")
    val mintempC: Any? = null,

    @field:SerializedName("daily_will_it_rain")
    val dailyWillItRain: Int? = null,

    @field:SerializedName("totalsnow_cm")
    val totalsnowCm: Any? = null,

    @field:SerializedName("avghumidity")
    val avghumidity: Any? = null,

    @field:SerializedName("condition")
    val condition: Condition? = null,

    @field:SerializedName("maxwind_kph")
    val maxwindKph: Any? = null,

    @field:SerializedName("daily_chance_of_rain")
    val dailyChanceOfRain: Int? = null,

    @field:SerializedName("totalprecip_mm")
    val totalprecipMm: Any? = null,

    @field:SerializedName("daily_will_it_snow")
    val dailyWillItSnow: Int? = null
)

data class Current(

    @field:SerializedName("cloud")
    val cloud: Int? = null,

    @field:SerializedName("wind_kph")
    val windKph: Any? = null,

    @field:SerializedName("feelslike_c")
    val feelslikeC: Any? = null,

    @field:SerializedName("last_updated")
    val lastUpdated: String? = null,

    @field:SerializedName("condition")
    val condition: Condition? = null,

    @field:SerializedName("is_day")
    val isDay: Int? = null,

    @field:SerializedName("humidity")
    val humidity: Int? = null,

    @field:SerializedName("wind_dir")
    val windDir: String? = null,

    @field:SerializedName("temp_c")
    val tempC: Any? = null,

    @field:SerializedName("gust_kph")
    val gustKph: Any? = null,

    @field:SerializedName("precip_mm")
    val precipMm: Any? = null
)
