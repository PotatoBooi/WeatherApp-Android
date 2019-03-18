package com.damianf.weatherapp.data.state.events

sealed class CurrentWeatherEvent{
    object OnStart : CurrentWeatherEvent()
    data class OnLocationChange(val location:String):CurrentWeatherEvent()
}