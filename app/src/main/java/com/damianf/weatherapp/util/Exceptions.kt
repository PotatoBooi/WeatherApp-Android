package com.damianf.weatherapp.util

import java.io.IOException

class NoConnectivityException: IOException(){
    override val message: String?
        get() = "No connection!"
}