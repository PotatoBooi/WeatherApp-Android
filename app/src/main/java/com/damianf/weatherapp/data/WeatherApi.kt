package com.damianf.weatherapp.data


import com.damianf.weatherapp.data.model.response.WeatherEntry
import com.damianf.weatherapp.data.network.ConnectivityInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.serjltt.moshi.adapters.FirstElement
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_URL = "http://api.openweathermap.org/data/2.5/"
const val API_KEY = "d54c3483661034d5da1002264c213c4e"

interface WeatherApi {
    @GET("weather")
    fun getCurrentWeather(
        @Query("q") cityName: String,
        @Query("lang") lang: String = "pl",
        @Query("units") units: String = "metric"
    ): Deferred<WeatherEntry>

    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): WeatherApi {
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("APPID", API_KEY)
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
            val moshi = Moshi.Builder().add(Wrapped.ADAPTER_FACTORY).add(FirstElement.ADAPTER_FACTORY).build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(API_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(WeatherApi::class.java)
        }
    }

}