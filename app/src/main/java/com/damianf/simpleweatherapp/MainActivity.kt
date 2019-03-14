package com.damianf.simpleweatherapp


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.damianf.simpleweatherapp.viewmodel.WeatherViewModel
import com.damianf.simpleweatherapp.viewmodel.WeatherViewModelFactory
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
        content_main.visibility = View.INVISIBLE
        progress_bar_main.visibility = View.VISIBLE
        viewModel.updateWeather(location)
        content_main.visibility = View.VISIBLE
        progress_bar_main.visibility = View.GONE
    }

    private fun goToSettings() {
        val intent = Intent(this, SettingsActivity::class.java).also { startActivity(it) }
    }

    private fun bindUI() {


        viewModel.loadWeather()
        viewModel.weather.observe(this, Observer { weather ->
            if (weather == null) return@Observer
            txt_temperature.text = "${weather.details.temperature}°C"
            txt_temperature_min_max.text = "${weather.details.temperatureMin} | ${weather.details.temperatureMax}°C"
            txt_city_name.text = weather.cityName
            txt_weather_description.text = weather.info.description
        })

//        btn_change_location.setOnClickListener{
//            updateLocation()
//        }
//        input_location.setOnEditorActionListener(object :TextView.OnEditorActionListener{
//            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
//                return if(actionId == EditorInfo.IME_ACTION_DONE){
//                    updateLocation()
//                    true
//                }else
//                    false
//            }
//
//        })
        progress_bar_main.visibility = View.GONE
    }

//    private fun updateLocation(){
//        if(input_location.text.toString().isNotEmpty()){
//            viewModel.updateWeather(input_location.text.toString().trim())
//
//            input_location.clearFocus()
//
//        }
//        else
//            Toast.makeText(this, "Bad location!",Toast.LENGTH_LONG).show()
//
//        input_location.text?.clear()
//    }

}
