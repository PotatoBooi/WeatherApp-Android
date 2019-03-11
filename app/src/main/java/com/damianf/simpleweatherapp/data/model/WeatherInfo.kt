package com.damianf.simpleweatherapp.data.model

import com.squareup.moshi.Json

data class WeatherDetails(
    @Json(name = "description")
    val description: String,
    @Json(name = "main")
    val condition: String,
    @Json(name = "icon")
    val icon: String
)