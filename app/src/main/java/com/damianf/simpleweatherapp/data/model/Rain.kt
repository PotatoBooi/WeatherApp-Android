package com.damianf.simpleweatherapp.data.model

import com.squareup.moshi.Json

data class Rain(
    @Json(name = "1h")
    val h: Double
)