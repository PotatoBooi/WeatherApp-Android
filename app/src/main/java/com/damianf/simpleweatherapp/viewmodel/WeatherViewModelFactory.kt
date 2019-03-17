package com.damianf.simpleweatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.damianf.simpleweatherapp.data.WeatherDataSource
import com.damianf.simpleweatherapp.data.repository.WeatherRepository

class WeatherViewModelFactory(
    private val weatherRepository: WeatherRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(weatherRepository) as T
    }
}