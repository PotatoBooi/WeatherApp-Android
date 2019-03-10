package com.damianf.simpleweatherapp.data.model

import com.squareup.moshi.Json

data class Wind(
    @Json(name = "deg")
    val deg: Int,
    @Json(name = "speed")
    val speed: Double
)