package com.damianf.weatherapp.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class Weather(
    val cityName: String,
    val description: String,
    val condition: String,
    val iconResource: Int,
    val temperature: Int,
    val temperatureMin: Int,
    val temperatureMax: Int,
    val pressure: Int,
    val humidity: Int,
    val cloudiness: Int,
    val updateTime: Long// - for later weather refreshing
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}