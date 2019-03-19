package com.damianf.weatherapp.util

import com.damianf.weatherapp.R
import com.damianf.weatherapp.data.model.entity.Weather
import com.damianf.weatherapp.data.model.response.WeatherEntry
import kotlin.math.roundToInt

internal val WeatherEntry.toWeatherModel : Weather
    get() = Weather(
        cityName,
        info.description.capitalize(),
        info.condition,
        "$iconUrl${info.icon}$iconFormat",
        details.temperature.roundToInt(),
        details.temperatureMin.roundToInt(),
        details.temperatureMax.roundToInt(),
        details.pressure.roundToInt(),
        details.humidity,
        clouds.value
    )

 val conditionMap = hashMapOf(
     "Clear" to R.drawable.ic_sun,
     "Clouds" to R.drawable.ic_cloudy,
     "Rain" to R.drawable.ic_rain,
     "Snow" to R.drawable.ic_snow,
     "Mist" to R.drawable.ic_mist,
     "Thunderstorm" to R.drawable.ic_storm
 )
