package com.damianf.simpleweatherapp

import android.app.Application
import com.damianf.simpleweatherapp.data.WeatherApi
import com.damianf.simpleweatherapp.data.WeatherDataSource
import com.damianf.simpleweatherapp.data.network.ConnectivityInterceptor
import com.damianf.simpleweatherapp.viewmodel.WeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinContainer
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class SimpleWeatherApp : Application(),KodeinAware{
    override val kodein by Kodein.lazy {
        import(androidXModule(this@SimpleWeatherApp))
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptor(instance()) }
        bind() from singleton { WeatherApi(instance()) }
        bind() from singleton { WeatherDataSource(instance())}
        bind() from provider {WeatherViewModelFactory(instance())}
    }

}