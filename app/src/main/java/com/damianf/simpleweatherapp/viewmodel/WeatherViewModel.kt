package com.damianf.simpleweatherapp.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.damianf.simpleweatherapp.data.WeatherDataSource
import com.damianf.simpleweatherapp.data.model.WeatherEntry
import com.damianf.simpleweatherapp.data.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import com.damianf.simpleweatherapp.data.Result
import com.damianf.simpleweatherapp.data.state.events.CurrentWeatherEvent

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel(), CoroutineScope {
    private val job = Job()
    private var currentLocation: String? = null
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private val _weather: MutableLiveData<WeatherEntry> = MutableLiveData()

    val weather: LiveData<WeatherEntry> by lazy {
        _weather
    }
    val loading = MutableLiveData<Unit>()
    val error = MutableLiveData<String>()
    fun handleEvent(event: CurrentWeatherEvent) {
        when (event) {
            is CurrentWeatherEvent.OnStart -> loadWeather()
            is CurrentWeatherEvent.OnLocationChange -> loadWeather(event.location)
        }

    }

    private fun loadWeather(location: String = "") = launch {
        val data = if (location == "") {
            repository.getWeather()
        } else
            repository.getWeather(location)

        when (data) {
            is Result.Value -> {_weather.value = data.value

            }
            is Result.Error -> {
                error.value = data.error.message
            }//handle errors
        }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }



}
