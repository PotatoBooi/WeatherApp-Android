package com.damianf.simpleweatherapp.data.model

import com.squareup.moshi.Json

data class Sys(
    @Json(name = "country")
    val country: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "message")
    val message: Double,
    @Json(name = "sunrise")
    val sunrise: Int,
    @Json(name = "sunset")
    val sunset: Int,
    @Json(name = "type")
    val type: Int
)