package com.damianf.weatherapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.damianf.weatherapp.data.model.entity.Weather

@Database(entities = [Location::class, Weather::class],version = 3)
abstract class WeatherDatabase : RoomDatabase(){
    abstract fun locationDao() : LocationDao
    abstract fun weatherDao() : WeatherDao
    companion object {
        @Volatile private var instance: WeatherDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance?: buildDatabase(context).also {instance=it}
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,WeatherDatabase::class.java,"weather.db")
                .fallbackToDestructiveMigration()
            .build()
    }

}