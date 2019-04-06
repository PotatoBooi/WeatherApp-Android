package com.damianf.weatherapp.ui.weather


import android.Manifest
import android.app.ProgressDialog.show
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.androidadvance.topsnackbar.TSnackbar
import com.damianf.weatherapp.R
import com.damianf.weatherapp.data.state.events.CurrentWeatherEvent
import com.damianf.weatherapp.ui.settings.SettingsActivity
import com.damianf.weatherapp.viewmodel.WeatherViewModel
import com.damianf.weatherapp.viewmodel.WeatherViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener
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
        supportActionBar?.title = getString(R.string.app_bar_title_string)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(WeatherViewModel::class.java)
        bindUI()
        viewModel.handleEvent(CurrentWeatherEvent.OnStart)
        if (!hasLocationPermission())
            askForPermissions()

    }

    override fun onResume() {
        super.onResume()
        viewModel.handleEvent(CurrentWeatherEvent.OnStart)
    }

    private fun askForPermissions() = Dexter.withActivity(this)
        .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        .withListener(
            SnackbarOnDeniedPermissionListener.Builder
                .with(content_main, "Lokalizacja jest wymagana")
                .withOpenSettingsButton(R.string.settings_title_string)
                .withDuration(Snackbar.LENGTH_INDEFINITE)
                .build()
        )
        .check()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_settings -> {
                goToSettings()
            }

            R.id.action_refresh -> {
                viewModel.handleEvent(CurrentWeatherEvent.OnStart)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun bindUI() {
        viewModel.weather.observe(this, Observer { weather ->
            if (weather == null) return@Observer
            txt_temperature.text = "${weather.temperature}°C"
            txt_temperature_min.text = "${weather.temperatureMin}°C"
            txt_temperature_max.text = "${weather.temperatureMax}°C"
            txt_pressure.text = weather.pressure.toString() + " pa"
            txt_clouds.text = weather.cloudiness.toString() + " %"
            txt_city_name.text = weather.cityName
            txt_weather_description.text = weather.description
            img_weather_icon.setImageDrawable(getDrawable(weather.iconResource))
            progress_bar_main.visibility = View.GONE
            txt_info.visibility = View.VISIBLE
        })
        viewModel.error.observe(this, Observer { error ->
            if (error == null) return@Observer
            showError(error)
        })
    }

    private fun updateWeather(location: String) {
        viewModel.handleEvent(CurrentWeatherEvent.OnLocationChange(location))
    }

    private fun goToSettings() {
        val intent = Intent(this, SettingsActivity::class.java).also { startActivity(it) }
    }


    private fun showError(error: Int) {
        TSnackbar.make(tsnackbar, error, TSnackbar.LENGTH_LONG)
            .apply {
                view.setBackgroundColor(getColor(R.color.design_error))
                show()
            }
    }

    private fun showLoading() {
        //
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this.applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}
