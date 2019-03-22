package com.damianf.weatherapp.data

import com.damianf.weatherapp.data.model.entity.Weather
import com.damianf.weatherapp.util.toWeatherModel
import retrofit2.HttpException


class WeatherDataSource(private val api: WeatherApi) {
    suspend fun getWeather(location: String): Result<Exception, Weather> {
        return try {
            val downloadedWeather =
                api.getCurrentWeather(location)
                    .await()

            Result.build { downloadedWeather.toWeatherModel }
        } catch (ex: Exception) {
            Result.build { throw ex }
        }
    }
}