package com.damianf.weatherapp.data.model.response

import com.squareup.moshi.Json

data class WeatherInfo(
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "main")
    val condition: String,
    @field:Json(name = "id")
    val conditionId: Int
)