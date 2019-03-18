package com.damianf.weatherapp.data.repository

import com.damianf.weatherapp.data.WeatherDataSource
import com.damianf.weatherapp.data.db.Location
import com.damianf.weatherapp.data.db.LocationDao
import com.damianf.weatherapp.data.model.response.WeatherEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import com.damianf.weatherapp.data.Result
import com.damianf.weatherapp.data.model.entity.Weather
import com.damianf.weatherapp.util.baseLocation

class WeatherRepository(
    private val dataSource: WeatherDataSource,
    private val locationDao: LocationDao
) {
    init {

    }

    suspend fun getWeather(location: String = ""): Result<Exception, Weather> = withContext(Dispatchers.IO) {

        val lastLocation = try {
            locationDao.getLastLocation().cityName
        } catch (ex: Exception) {
            baseLocation
        }
        var newLocation = location
        if (location == "") newLocation = lastLocation
        val data = dataSource.getWeather(newLocation)
        when (data) {
            is Result.Value -> locationDao.setLocation(Location(data.value.cityName))
            //is Result.Error -> locationDao.setLocation(Location(lastLocation))
        }
        return@withContext data
    }
}