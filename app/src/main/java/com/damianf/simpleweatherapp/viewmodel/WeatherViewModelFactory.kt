package com.damianf.simpleweatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.damianf.simpleweatherapp.data.WeatherDataSource

class WeatherViewModelFactory(
    private val weatherDataSource: WeatherDataSource
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(weatherDataSource) as T
    }
}