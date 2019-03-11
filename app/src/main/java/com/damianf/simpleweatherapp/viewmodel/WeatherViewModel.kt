package com.damianf.simpleweatherapp.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.damianf.simpleweatherapp.data.WeatherDataSource
import com.damianf.simpleweatherapp.data.model.WeatherEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CurrentWeatherViewModel(private val dataSource: WeatherDataSource) : ViewModel(),CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    val weather: LiveData<WeatherEntry> by lazy {
        dataSource.currentWeather
    }
    fun loadWeather() = launch {
        dataSource.getWeather("Gliwice","metric","pl")
    }
    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }

}
