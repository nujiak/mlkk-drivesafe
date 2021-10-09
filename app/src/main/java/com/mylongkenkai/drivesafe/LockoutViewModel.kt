package com.mylongkenkai.drivesafe


import android.app.Application
import android.content.Context
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.mylongkenkai.drivesafe.data.AppDatabase
import com.mylongkenkai.drivesafe.data.Exclusion
import com.mylongkenkai.drivesafe.data.Record
import kotlinx.coroutines.*
import java.util.*


class LockoutViewModel(app: Application) : AndroidViewModel(app) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val recordsDb = AppDatabase.getInstance(app).recordDao()
    private val exclusionDb = AppDatabase.getInstance(app).exclusionDao()

    val exclusions = exclusionDb.getAll()

    private var exclusionsList: List<Exclusion> = emptyList()

    fun updateExclusionsList(exclusionsList: List<Exclusion>) {
        this.exclusionsList = exclusionsList
        Log.w(this::class.simpleName, "$exclusionsList")
    }

    private val phoneStateListener = object : PhoneStateListener() {
        override fun onCallStateChanged(state: Int, incomingNumber: String) {
            when (state) {
                TelephonyManager.CALL_STATE_OFFHOOK -> {
                    val isExcluded = exclusionsList.any {
                        it.phoneNumber == incomingNumber.toInt()
                    }
                    if (!isExcluded) {
                        val record = Record(0, Date(), Tag.CALL)
                        addRecord(record)
                    }
                }
                TelephonyManager.CALL_STATE_IDLE -> {
                }
                TelephonyManager.CALL_STATE_RINGING -> {
                }
            }
        }
    }

    init {
        val telephonyManager = app.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
    }


    fun addRecord(record: Record) = uiScope.launch {
        insertRecord(record)
    }

    private suspend fun insertRecord(record: Record) = withContext(Dispatchers.IO) {
        recordsDb.insert(record)
    }
}