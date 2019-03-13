package com.damianf.simpleweatherapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.damianf.simpleweatherapp.data.model.WeatherEntry
import com.damianf.simpleweatherapp.data.network.NoConnectivityException


class WeatherDataSource(private val api: WeatherApi) {
    private val _currentWeather = MutableLiveData<WeatherEntry>()
    val currentWeather: LiveData<WeatherEntry>
        get() = _currentWeather

    suspend fun getWeather(location: String) {
        try {
            val downloadedWeather =
                api.getCurrentWeather(location)
                    .await()

            _currentWeather.postValue(downloadedWeather)
        }catch (ex:NoConnectivityException){
            Log.e("Connectivity","No internet connection")
        }
    }
    }