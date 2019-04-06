package com.damianf.weatherapp

import android.app.Application
import android.content.Context
import com.damianf.weatherapp.data.WeatherApi
import com.damianf.weatherapp.data.WeatherDataSource
import com.damianf.weatherapp.data.db.WeatherDatabase
import com.damianf.weatherapp.data.location.LocationProvider
import com.damianf.weatherapp.data.network.ConnectivityInterceptor
import com.damianf.weatherapp.data.repository.WeatherRepository
import com.damianf.weatherapp.viewmodel.WeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class WeatherApp : Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        import(androidXModule(this@WeatherApp))
        bind() from singleton { WeatherDatabase(instance()) }
        bind() from singleton { instance<WeatherDatabase>().locationDao() }
        bind() from singleton { instance<WeatherDatabase>().weatherDao() }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProvider(instance(), instance()) }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptor(instance()) }
        bind() from singleton { WeatherApi(instance()) }
        bind() from singleton { WeatherDataSource(instance()) }
        bind() from singleton { WeatherRepository(instance(), instance(), instance(),instance()) }
        bind() from provider { WeatherViewModelFactory(instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

}