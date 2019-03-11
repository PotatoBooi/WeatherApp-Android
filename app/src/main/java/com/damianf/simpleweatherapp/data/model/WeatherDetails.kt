package com.damianf.simpleweatherapp.data.model

import com.squareup.moshi.Json

data class Main(
    @Json(name = "humidity")
    val humidity: Int,
    @Json(name = "pressure")
    val pressure: Int,
    @Json(name = "temp")
    val temp: Double,
    @Json(name = "temp_max")
    val tempMax: Double,
    @Json(name = "temp_min")
    val tempMin: Double
)