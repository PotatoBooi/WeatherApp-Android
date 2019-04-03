package com.damianf.weatherapp.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.damianf.weatherapp.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

}