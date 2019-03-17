package com.damianf.simpleweatherapp.data.model

import com.serjltt.moshi.adapters.FirstElement
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Json


data class WeatherEntry(
    @field:Json(name = "name")
    val cityName: String,
    @FirstElement
    @field:Json(name = "weather")
    val info: WeatherInfo,
    @field:Json(name = "main")
    val details: WeatherDetails,
    @field:Json(name = "clouds")
    val clouds: Clouds,
    @field:Json(name = "dt")
    val updateTime: Long
)

data class Clouds(@field:Json(name = "all") val value: Int)