package com.damianf.weatherapp.data.model.entity

//@Entity(tableName = "weather_table")
data class Weather(
    val cityName: String,
    val description: String,
    val condition: String,
    val iconUrl: String,
    val temperature: Int,
    val temperatureMin: Int,
    val temperatureMax: Int,
    val pressure: Int,
    val humidity: Int
//val updateTime: Long - for later weather refreshing
)