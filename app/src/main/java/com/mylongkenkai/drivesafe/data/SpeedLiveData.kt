package com.mylongkenkai.drivesafe.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.lifecycle.LiveData

class SpeedLiveData(context: Context) : LiveData<Float>(), LocationListener {

    private val locationManager: LocationManager
            by lazy { context.getSystemService(LOCATION_SERVICE) as LocationManager }

    private var lastLocation : Location? = null

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()

        val provider = locationManager.getBestProvider(Criteria(), true) ?: return

        locationManager.requestLocationUpdates(provider, 1000, 0f, this)
    }

    override fun onInactive() {
        super.onInactive()

        locationManager.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location) {
        if (lastLocation == null) {
            lastLocation = location
        }

        lastLocation?.let {
            if (location.time > it.time) {
                val distance = location.distanceTo(it)
                val duration = location.time - it.time
                val speed = distance / (duration / 1000)
                value = speed
            }
        }
        lastLocation = location
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
}