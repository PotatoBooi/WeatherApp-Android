package com.damianf.weatherapp.data

import com.damianf.weatherapp.data.model.entity.Weather
import com.damianf.weatherapp.util.toWeatherModel


class WeatherDataSource(private val api: WeatherApi) {
    suspend fun getWeather(cityName: String? = null, lat: Double?= null, lon: Double? = null): Result<Exception, Weather> {
        return try {
            val downloadedWeather =
                api.getCurrentWeather(cityName, lat = lat, lon = lon)
                    .await()

            Result.build { downloadedWeather.toWeatherModel }
        } catch (ex: Exception) {
            Result.build { throw ex }
        }
    }
}