package com.mylongkenkai.drivesafe


import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import com.mylongkenkai.drivesafe.data.AppDatabase
import com.mylongkenkai.drivesafe.data.Exclusion
import com.mylongkenkai.drivesafe.data.Record
import com.mylongkenkai.drivesafe.data.SpeedLiveData
import kotlinx.coroutines.*
import java.util.*


class LockoutViewModel(app: Application) : AndroidViewModel(app) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val recordsDb = AppDatabase.getInstance(app).recordDao()
    private val exclusionDb = AppDatabase.getInstance(app).exclusionDao()

    val exclusions = exclusionDb.getAll()

    val speed = SpeedLiveData(app)

    private var exclusionsList: List<Exclusion> = emptyList()

    fun updateExclusionsList(exclusionsList: List<Exclusion>) {
        this.exclusionsList = exclusionsList
    }

    private val phoneStateListener = object : PhoneStateListener() {
        var previousState = TelephonyManager.CALL_STATE_IDLE
        override fun onCallStateChanged(state: Int, incomingNumber: String) {
            when (state) {
                TelephonyManager.CALL_STATE_OFFHOOK -> {
                    val isExcluded = exclusionsList.any {
                        it.phoneNumber == incomingNumber.toInt()
                    }
                    if (!isExcluded) {
                        val record = Record(0, Date(), Tag.CALL)
                        addRecord(record)
                    } else {
                        val record = Record(0, Date(), Tag.EXCALL)
                        addRecord(record)
                    }
                    previousState = TelephonyManager.CALL_STATE_OFFHOOK
                }
                TelephonyManager.CALL_STATE_IDLE -> {
                    val isExcluded = exclusionsList.any {
                        it.phoneNumber == incomingNumber.toInt()
                    }
                    if (!isExcluded && previousState == TelephonyManager.CALL_STATE_OFFHOOK) {
                        val record = Record(0, Date(), Tag.CALLEND)
                        addRecord(record)
                    }
                    val notificationManager = app.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE)
                    previousState = TelephonyManager.CALL_STATE_IDLE
                }
                TelephonyManager.CALL_STATE_RINGING -> {
                    val isExcluded = exclusionsList.any {
                        it.phoneNumber == incomingNumber.toInt()
                    }
                    if (isExcluded) {
                        val notificationManager = app.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
                        notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
                    }
                    previousState = TelephonyManager.CALL_STATE_RINGING
                }
            }
        }
    }

    init {
        val telephonyManager = app.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
    }

    fun addRecordEnd() {
        if (speed.value == null || speed.value!! < 10) {
            val record = Record(0, Date(), Tag.END)
            addRecord(record)
        } else {
            val record = Record(0, Date(), Tag.PREMATURE)
            addRecord(record)
        }
    }

    fun addRecord(record: Record) = uiScope.launch {
        insertRecord(record)
    }

    private suspend fun insertRecord(record: Record) = withContext(Dispatchers.IO) {
        recordsDb.insert(record)
    }
}
