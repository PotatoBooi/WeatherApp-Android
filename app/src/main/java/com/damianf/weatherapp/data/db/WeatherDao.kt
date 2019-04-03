package com.damianf.weatherapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.damianf.weatherapp.data.model.entity.Weather

@Dao
interface WeatherDao {
    @Query("SELECT * from weather_table")
    fun getLastWeather(): Weather

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertWeather(weather: Weather)
}