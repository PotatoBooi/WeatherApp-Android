package com.damianf.weatherapp.util

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
        details.pressure,
        details.humidity
    )