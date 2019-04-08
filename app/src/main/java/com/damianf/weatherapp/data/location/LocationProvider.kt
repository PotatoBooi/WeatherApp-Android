package com.damianf.weatherapp.data.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.preference.PreferenceManager
import androidx.core.content.ContextCompat
import com.damianf.weatherapp.util.PermissionNotGrantedException
import com.damianf.weatherapp.util.asDeferred
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Deferred
import java.lang.Exception
import com.damianf.weatherapp.data.Result

const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val CUSTOM_LOCATION = "CUSTOM_LOCATION"
const val LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
class LocationProvider(
    private val context: Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : PreferenceProvider(context) {
    private val appContext = context.applicationContext

    suspend fun getDeviceLocation(): Result<Exception,LocationState> {
        if (isUsingDeviceLocation()) {
            try {
                val deviceLocation = getLastDeviceLocation().await()
                    ?: return Result.build { LocationState.Custom(getCustomLocationName()!!) }
                return Result.build { LocationState.Device(deviceLocation.latitude,deviceLocation.longitude) }
            } catch (e: PermissionNotGrantedException) {
                return Result.build { throw e }
            }
        }
        else
            return Result.build { LocationState.Custom(getCustomLocationName()!!) }
    }


    private fun getCustomLocationName(): String? {
        return preferences.getString(CUSTOM_LOCATION, null)
    }

    private fun isUsingDeviceLocation(): Boolean {
        return preferences.getBoolean(USE_DEVICE_LOCATION, true)
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            appContext,
            LOCATION_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED
    }


    @SuppressLint("MissingPermission")
    private fun getLastDeviceLocation(): Deferred<Location?> {
        return if (hasLocationPermission())
            fusedLocationProviderClient.lastLocation.asDeferred()
        else
            throw PermissionNotGrantedException()
    }
}

abstract class PreferenceProvider(context: Context) {
    private val appContext = context.applicationContext

    protected val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)
}