package com.mylongkenkai.drivesafe.work

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.mylongkenkai.drivesafe.LockoutActivity
import com.mylongkenkai.drivesafe.R
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Long running Worker that constantly detects speed in the background and launches LockoutActivity
 *
 * @constructor Creates a DetectionWorker
 * @param appContext
 * @param workerParams
 */
class DetectionWorker(appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams)  {

    override suspend fun doWork(): Result {

        // Begin detection
        detectByLocation()
        detectBySensor()

        // Show notification
        setForeground(createForegroundInfo())

        return Result.success()
    }

    @SuppressLint("MissingPermission")
    private fun detectByLocation() {
        Log.i(this::class.simpleName, "Attempting to detect by location")
        val locationManager = applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager

        val provider = locationManager.getBestProvider(Criteria(), true) ?: return

        locationManager.requestLocationUpdates(provider, 1000, 0.0f, listener, applicationContext.mainLooper)
    }

    private var lastLocation : Location? = null
    private var lastUpdateTime = System.currentTimeMillis()

    val listener = object : LocationListener {

        override fun onLocationChanged(location: Location) {
            Log.i(this::class.simpleName, "Location updated")

            val currentTime = System.currentTimeMillis()

            if (currentTime - lastUpdateTime < 5000) {
                return
            }

            if (lastLocation != null) {
                val distance = location.distanceTo(lastLocation)      // in metres
                val duration = currentTime - lastUpdateTime     // in milliseconds
                val speed = distance / (duration / 1000)        // in metres per second
                Log.i(this::class.simpleName, "Speed: $speed")
                if (speed > 10) {
                    startLockout()
                }
            }
            lastLocation = location
            lastUpdateTime = currentTime
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }
    }

    private fun detectBySensor() {
        val sensorManager = applicationContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)

        sensorManager.registerListener(object : SensorEventListener {

            override fun onSensorChanged(event: SensorEvent?) {
                if (event == null) {
                    return
                }
                val values = event.values
                if (isDriving(values[0], values[1], values[2])) {
                    startLockout()
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, p1: Int) {}
        }, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    private fun startLockout() {
        val lockoutIntent = Intent(applicationContext, LockoutActivity::class.java)
        lockoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        applicationContext.startActivity(lockoutIntent)
    }

    private fun isDriving(x: Float, y: Float, z: Float): Boolean {
        return sqrt(x.pow(2) + y.pow(2) + z.pow(2)) > 20
    }

    private fun createForegroundInfo(): ForegroundInfo {
        val id = applicationContext.getString(R.string.notification_channel_id)
        val title = applicationContext.getString(R.string.app_name)
        val stop = applicationContext.getString(R.string.stop)

        val cancelIntent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(getId())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        val notification = NotificationCompat.Builder(applicationContext, id)
            .setContentTitle(title)
            .setTicker(title)
            .setOngoing(true)
            .setChannelId(id)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .addAction(android.R.drawable.ic_delete, stop, cancelIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        return ForegroundInfo(0, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = applicationContext.getString(R.string.notification_channel_id)
        val descriptionText = applicationContext.getString(R.string.notification_channel_desc)
        val importance = NotificationManager.IMPORTANCE_LOW
        val mChannel = NotificationChannel(name, name, importance)
        mChannel.description = descriptionText
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        val notificationManager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
    }
}