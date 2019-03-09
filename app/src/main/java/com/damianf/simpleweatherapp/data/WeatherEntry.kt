package com.damianf.simpleweatherapp.data

import com.squareup.moshi.Json

data class WeatherEntry(
    @Json(name = "name")
    val cityName: String,
    @Json(name = "main.temp")
    val temperature: Double,
    @Json(name = "main.temp_min")
    val tempMin: Double,
    @Json(name = "main.temp_max")
    val tempMax: Double
)