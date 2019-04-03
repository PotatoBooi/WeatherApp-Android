package com.damianf.weatherapp.ui.weather


import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.androidadvance.topsnackbar.TSnackbar
import com.damianf.weatherapp.R
import com.damianf.weatherapp.data.state.events.CurrentWeatherEvent
import com.damianf.weatherapp.ui.settings.SettingsActivity
import com.damianf.weatherapp.viewmodel.WeatherViewModel
import com.damianf.weatherapp.viewmodel.WeatherViewModelFactory
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
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
        askForPermissions()
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(WeatherViewModel::class.java)
        bindUI()
        viewModel.handleEvent(CurrentWeatherEvent.OnStart)
    }


    private fun askForPermissions() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    //update location



                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {

                }

            }).check()
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
        //need to fix this, snackbar must show below the toolbar\
        TSnackbar.make(content_main, error, TSnackbar.LENGTH_LONG)
            .apply {
                view.setBackgroundColor(getColor(R.color.design_error))
                show()
            }
    }

    private fun showLoading() {
        //
    }
}
