package com.damianf.simpleweatherapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.damianf.simpleweatherapp.data.model.WeatherEntry
import com.damianf.simpleweatherapp.data.network.NoConnectivityException
import java.lang.Exception


class WeatherDataSource(private val api: WeatherApi) {
    suspend fun getWeather(location: String): Result<Exception, WeatherEntry> {
        return try {
            val downloadedWeather =
                api.getCurrentWeather(location)
                    .await()

            Result.build { downloadedWeather }
        } catch (ex: Exception) {
            Log.e("Connectivity", "No internet connection")
            Result.build { throw ex }

        }
    }
}