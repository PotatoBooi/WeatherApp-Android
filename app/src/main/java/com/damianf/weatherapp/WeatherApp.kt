package com.damianf.weatherapp

import android.app.Application
import com.damianf.weatherapp.data.WeatherApi
import com.damianf.weatherapp.data.WeatherDataSource
import com.damianf.weatherapp.data.db.WeatherDatabase
import com.damianf.weatherapp.data.network.ConnectivityInterceptor
import com.damianf.weatherapp.data.repository.WeatherRepository
import com.damianf.weatherapp.viewmodel.WeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class WeatherApp : Application(),KodeinAware{
    override val kodein by Kodein.lazy {
        import(androidXModule(this@WeatherApp))
        bind() from singleton{ WeatherDatabase(instance()) }
        bind() from singleton{ instance<WeatherDatabase>().locationDao() }

        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptor(instance()) }
        bind() from singleton { WeatherApi(instance()) }
        bind() from singleton { WeatherDataSource(instance())}
        bind() from singleton {WeatherRepository(instance(),instance())}
        bind() from provider {WeatherViewModelFactory(instance())}

    }

}