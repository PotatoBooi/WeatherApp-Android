package com.damianf.weatherapp


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.damianf.weatherapp.data.state.events.CurrentWeatherEvent
import com.damianf.weatherapp.viewmodel.WeatherViewModel
import com.damianf.weatherapp.viewmodel.WeatherViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val viewModelFactory: WeatherViewModelFactory by instance()
    private lateinit var viewModel: WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(WeatherViewModel::class.java)
        bindUI()
        viewModel.handleEvent(CurrentWeatherEvent.OnStart)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_settings -> {
                goToSettings()
            }
            R.id.action_search -> {
                val search = item.actionView as SearchView
                search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return if (query != null && query.isNotEmpty()) {
                            updateWeather(query)
                            item.collapseActionView()
                            true
                        } else false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return true
                    }
                })
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateWeather(location: String) {
        viewModel.handleEvent(CurrentWeatherEvent.OnLocationChange(location))
    }

    private fun goToSettings() {
        val intent = Intent(this, SettingsActivity::class.java).also { startActivity(it) }
    }

    private fun bindUI() {
        viewModel.weather.observe(this, Observer { weather ->
            if (weather == null) return@Observer
            txt_temperature.text = "${weather.temperature}°C"
            txt_temperature_min_max.text = "${weather.temperatureMin} | ${weather.temperatureMax}°C"
            txt_city_name.text = weather.cityName
            txt_weather_description.text = weather.description
            Glide.with(this@MainActivity)
                .load(weather.iconUrl)
                .into(img_weather_icon)
            progress_bar_main.visibility = View.GONE
        })
        viewModel.error.observe(this, Observer { error ->
            if(error == null) return@Observer
            showError(error)
        })
        viewModel.loading.observe(this, Observer { showLoading() })

    }

    private fun showError(error: String) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
       //
    }
}