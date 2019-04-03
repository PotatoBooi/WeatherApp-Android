package com.damianf.weatherapp.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.damianf.weatherapp.R
import com.damianf.weatherapp.data.model.response.WeatherEntry
import com.damianf.weatherapp.data.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import com.damianf.weatherapp.data.Result
import com.damianf.weatherapp.data.model.entity.Weather
import com.damianf.weatherapp.data.state.events.CurrentWeatherEvent
import com.damianf.weatherapp.util.NoConnectivityException
import com.damianf.weatherapp.util.PermissionNotGrantedException
import com.damianf.weatherapp.util.SingleLiveEvent
import retrofit2.HttpException

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private val _weather: MutableLiveData<Weather> = MutableLiveData()

    val weather: LiveData<Weather> by lazy {
        _weather
    }
    // need better error messaging and snackbar
    val error = SingleLiveEvent<Int>()
    fun handleEvent(event: CurrentWeatherEvent) {
        when (event) {
            is CurrentWeatherEvent.OnStart -> loadWeather()
            is CurrentWeatherEvent.OnLocationChange -> loadWeather(event.location)
        }

    }

    private fun loadWeather(location: String = "") = launch {
        val data = repository.getWeather(location)
        when (data) {
            is Result.Value -> {_weather.value = data.value

            }
            is Result.Error -> {
                error.value =
                    when(data.error){
                        is HttpException -> R.string.error_not_found
                        is NoConnectivityException -> R.string.error_no_connection
                        is PermissionNotGrantedException -> R.string.error_no_permissions
                        else -> R.string.error_common
                    }
            }//handle errors
        }
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }



}
