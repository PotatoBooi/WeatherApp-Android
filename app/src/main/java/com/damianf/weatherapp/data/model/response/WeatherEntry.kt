package com.damianf.weatherapp.data.model.response

import com.serjltt.moshi.adapters.FirstElement
import com.squareup.moshi.Json


data class WeatherEntry(
    @field:Json(name = "name")
    val cityName: String,
    @field:Json(name = "coord")
    val coords: Coords,
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
data class Coords(
    @field:Json(name = "lon")
    val longitude: Double,
    @field:Json(name = "lat")
    val latitude: Double
)