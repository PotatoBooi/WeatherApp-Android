package com.damianf.simpleweatherapp


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.damianf.simpleweatherapp.data.WeatherApi
import com.damianf.simpleweatherapp.data.WeatherDataSource
import com.damianf.simpleweatherapp.data.network.ConnectivityInterceptor
import com.damianf.simpleweatherapp.viewmodel.CurrentWeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val dataSource = WeatherDataSource(WeatherApi(ConnectivityInterceptor(this.applicationContext)))
    private lateinit var viewModel: CurrentWeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = CurrentWeatherViewModel(dataSource)
        viewModel.loadWeather()
        viewModel.weather.observe(this, Observer {
            txt_name.text = viewModel.weather.value?.cityName
            txt_temperature.text = viewModel.weather.value?.temperature.toString()
        })
    }
}
