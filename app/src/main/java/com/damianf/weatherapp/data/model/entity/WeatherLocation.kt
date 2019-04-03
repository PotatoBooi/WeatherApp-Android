package com.damianf.weatherapp.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

const val LOCATION_ID = 0

@Entity(tableName = "weather_location")
data class WeatherLocation(
    val cityName: String,
    val longitude: Double,
    val latitude: Double
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = LOCATION_ID
}