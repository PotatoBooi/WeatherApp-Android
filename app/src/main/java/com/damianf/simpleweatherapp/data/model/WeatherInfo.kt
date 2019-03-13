package com.damianf.simpleweatherapp.data.model

import com.squareup.moshi.Json

data class WeatherInfo(
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "main")
    val condition: String,
    @field:Json(name = "icon")
    val icon: String
)