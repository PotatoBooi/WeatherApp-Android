package com.damianf.simpleweatherapp.data

import com.squareup.moshi.Json


data class WeatherEntry(
    @field:Json(name = "city_name")
    val name: String,
    @field:Json(name = "message")
    val message: Double
//    @field:Json(name = "main.temp")
//    val temperature: Double,
//    @field:Json(name = "main.temp_min")
//    val tempMin: Double,
//    @field:Json(name = "main.temp_max")
//    val tempMax: Double
)