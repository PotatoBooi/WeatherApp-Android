package com.damianf.weatherapp.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NavUtils
import com.damianf.weatherapp.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setSupportActionBar(toolbar_settings)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.settings_fragment,
                SettingsFragment()
            )
            .commit()

    }

    override fun onBackPressed() {
        NavUtils.navigateUpFromSameTask(this)
    }

}
