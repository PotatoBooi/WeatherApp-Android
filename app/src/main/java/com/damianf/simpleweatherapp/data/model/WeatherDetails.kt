package com.damianf.simpleweatherapp.data.model

import com.squareup.moshi.Json

data class WeatherDetails(
    @field:Json(name = "humidity")
    val humidity: Int,
    @field:Json(name = "pressure")
    val pressure: Int,
    @field:Json(name = "temp")
    val temperature: Double,
    @field:Json(name = "temp_max")
    val temperatureMax: Double,
    @field:Json(name = "temp_min")
    val temperatureMin: Double
)