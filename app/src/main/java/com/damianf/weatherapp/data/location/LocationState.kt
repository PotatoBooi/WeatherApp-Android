package com.damianf.weatherapp.data.location

sealed class LocationState{
    data class Device(val latitude: Double, val longitude: Double):LocationState()
    data class Custom(val name: String):LocationState()
}