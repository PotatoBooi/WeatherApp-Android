package com.damianf.simpleweatherapp.data.state.events

sealed class CurrentWeatherEvent{
    object OnStart : CurrentWeatherEvent()
    data class OnLocationChange(val location:String):CurrentWeatherEvent()
}