package com.mylongkenkai.drivesafe.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.LiveData
import kotlin.math.pow
import kotlin.math.sqrt

class LinearAccelerometerLiveData(context: Context) : LiveData<Float>(), SensorEventListener {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var sensor: Sensor? = null

    override fun onActive() {
        super.onActive()
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) {
            return
        }
        val values = event.values
        value = getMagnitude(values[0], values[1], values[2])
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }

    /**
     * Calculates the magnitude of a 3D vector given the components
     *
     * @param x magnitude in the x direction
     * @param y magnitude in the y-direction
     * @param z magnitude in the z-direction
     * @return
     */
    private fun getMagnitude(x: Float, y: Float, z: Float) : Float {
        return sqrt(x.pow(2) + y.pow(2) + z.pow(2))
    }

    override fun onInactive() {
        super.onInactive()
        sensorManager.unregisterListener(this)
    }
}