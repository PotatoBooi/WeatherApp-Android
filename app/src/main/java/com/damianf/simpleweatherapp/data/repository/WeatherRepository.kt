package com.damianf.simpleweatherapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.damianf.simpleweatherapp.data.WeatherDataSource
import com.damianf.simpleweatherapp.data.db.Location
import com.damianf.simpleweatherapp.data.db.LocationDao
import com.damianf.simpleweatherapp.data.model.WeatherEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import com.damianf.simpleweatherapp.data.Result

class WeatherRepository(
    private val dataSource: WeatherDataSource,
    private val locationDao: LocationDao
) {
    init {

    }

    suspend fun getWeather(location: String = ""): Result<Exception, WeatherEntry>  = withContext(Dispatchers.IO){
            val lastLocation = locationDao.getLastLocation().cityName
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