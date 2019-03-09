package com.damianf.simpleweatherapp.data


import com.damianf.simpleweatherapp.data.network.ConnectivityInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_URL = "http://api.openweathermap.org/data/2.5/forecast"
const val API_KEY = "d54c3483661034d5da1002264c213c4e"

interface WeatherApi {
    @GET(".")
    fun getCurrentWeather(
        @Query("q") cityName: String,
        @Query("lang") lang: String,
        @Query("units") unit: String
    ): Deferred<WeatherEntry>

    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): WeatherApi {
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("APP_ID", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(API_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
        }
    }

}