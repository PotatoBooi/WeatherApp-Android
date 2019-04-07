package com.damianf.weatherapp.util

import com.damianf.weatherapp.R
import com.damianf.weatherapp.data.model.entity.Weather
import com.damianf.weatherapp.data.model.response.WeatherEntry
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import retrofit2.HttpException
import kotlin.math.roundToInt

fun <T> Task<T>.asDeferred(): Deferred<T> {
    val deferred = CompletableDeferred<T>()

    this.addOnSuccessListener { result ->
        deferred.complete(result)
    }

    this.addOnFailureListener { exception ->
        deferred.completeExceptionally(exception)
    }

    return deferred
}


internal val WeatherEntry.toWeatherModel: Weather
    get() = Weather(
        cityName,
        info.description.capitalize(),
        info.condition,
        conditionMap[info.conditionId]!!,
        details.temperature.roundToInt(),
        details.temperatureMin.roundToInt(),
        details.temperatureMax.roundToInt(),
        details.pressure.roundToInt(),
        details.humidity,
        clouds.value,
        ZonedDateTime.ofInstant(
            Instant.ofEpochSecond(updateTime),
            ZoneId.systemDefault()).toLocalTime()
            .toString()
    )

val conditionMap = hashMapOf(
    //sun
    800 to R.drawable.ic_sun,
    //clouds
    801 to R.drawable.ic_cloudy,
    802 to R.drawable.ic_cloud2,
    803 to R.drawable.ic_cloud2,
    804 to R.drawable.ic_cloud2,
    //mist
    701 to R.drawable.ic_mist,
    711 to R.drawable.ic_mist,
    721 to R.drawable.ic_mist,
    731 to R.drawable.ic_mist,
    //snow
    600 to R.drawable.ic_snow,
    601 to R.drawable.ic_snow,
    602 to R.drawable.ic_snow,
    611 to R.drawable.ic_snowing,
    612 to R.drawable.ic_snowing,
    613 to R.drawable.ic_snowing,
    615 to R.drawable.ic_snowing,
    616 to R.drawable.ic_snowing,
    620 to R.drawable.ic_snowing,
    621 to R.drawable.ic_snowing,
    622 to R.drawable.ic_snowing,
    //rain
    500 to R.drawable.ic_rain2,
    501 to R.drawable.ic_rain2,
    502 to R.drawable.ic_rain,
    503 to R.drawable.ic_rain,
    504 to R.drawable.ic_rain,
    511 to R.drawable.ic_rain,
    520 to R.drawable.ic_rain,
    521 to R.drawable.ic_rain,
    //Storm
    200 to R.drawable.ic_storm,
    201 to R.drawable.ic_storm,
    202 to R.drawable.ic_storm,
    210 to R.drawable.ic_storm,
    211 to R.drawable.ic_storm,
    212 to R.drawable.ic_storm,
    221 to R.drawable.ic_storm,
    230 to R.drawable.ic_storm,
    231 to R.drawable.ic_storm,
    232 to R.drawable.ic_storm

)