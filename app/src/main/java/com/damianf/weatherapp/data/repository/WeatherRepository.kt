package com.damianf.weatherapp.data.repository

import android.util.Log
import com.damianf.weatherapp.data.Result
import com.damianf.weatherapp.data.WeatherDataSource
import com.damianf.weatherapp.data.db.Location
import com.damianf.weatherapp.data.db.LocationDao
import com.damianf.weatherapp.data.db.WeatherDao
import com.damianf.weatherapp.data.location.LocationProvider
import com.damianf.weatherapp.data.location.LocationState
import com.damianf.weatherapp.data.model.entity.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

class WeatherRepository(
    private val dataSource: WeatherDataSource,
    private val locationDao: LocationDao,
    private val weatherDao: WeatherDao,
    private val locationProvider: LocationProvider
) {
    init {

    }

    suspend fun getWeather(location: String = ""): Result<Exception, Weather> =
        withContext(Dispatchers.IO) {
            val deviceLocation = locationProvider.getDeviceLocation()

            val data = when (deviceLocation) {
                is Result.Value -> {
                    when (deviceLocation.value) {
                        is LocationState.Custom -> dataSource.getWeather(deviceLocation.value.name)
                        is LocationState.Device -> dataSource.getWeather(
                            null,
                            deviceLocation.value.latitude,
                            deviceLocation.value.longitude
                        )
                    }
                }
                is Result.Error -> return@withContext Result.build { throw deviceLocation.error as Throwable }

            }

//            val lastLocation = try {
//                locationDao.getLastLocation().cityName
//            } catch (ex: Exception) {
//                baseLocation
//            }
//            var newLocation = location
//            if (location == "") newLocation = lastLocation
//            val data =
//                if (isCustom)
//                    getByCustomLocation(newLocation)
//                else
//                    getByDeviceLocation()
            when (data) {
                is Result.Value -> {
                    locationDao.setLocation(Location(data.value.cityName))
                    updateWeather(data.value)
                }
                //is Result.Error -> locationDao.setLocation(Location(lastLocation))
            }

            return@withContext data
        }

    suspend fun getWeatherOffline(): Weather = withContext(Dispatchers.IO) {
        weatherDao.getLastWeather()
    }

    private suspend fun updateWeather(weather: Weather) =
        withContext(Dispatchers.IO) {
            weatherDao.upsertWeather(weather)
        }

}