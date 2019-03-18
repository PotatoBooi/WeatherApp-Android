package com.damianf.weatherapp.data

import android.util.Log
import com.damianf.weatherapp.data.model.entity.Weather
import com.damianf.weatherapp.data.model.response.WeatherEntry
import com.damianf.weatherapp.util.toWeatherModel
import java.lang.Exception


class WeatherDataSource(private val api: WeatherApi) {
    suspend fun getWeather(location: String): Result<Exception, Weather> {
        return try {
            val downloadedWeather =
                api.getCurrentWeather(location)
                    .await()

            Result.build { downloadedWeather.toWeatherModel }
        } catch (ex: Exception) {
            Log.e("Connectivity", "No internet connection")
            Result.build { throw ex }

        }
    }
}