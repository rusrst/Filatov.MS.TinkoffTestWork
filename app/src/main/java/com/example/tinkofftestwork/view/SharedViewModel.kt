package com.example.tinkofftestwork.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tinkofftestwork.WorkerThread
import com.example.tinkofftestwork.data.DataClass

class SharedViewModel: ViewModel() {
    var workerThread: WorkerThread? = null
    var latestDataLiveData = MutableLiveData<List<DataClass>?>()
    var latestStartDownload = false
    var latestData = mutableListOf<DataClass>()
    var currentCounterLast: Int? = null
    var currentPageCounterLast: Int? = null
    var currentCounterHot: Int = 0
    var currentCounterTop: Int = 0
    override fun onCleared() {
        super.onCleared()
        workerThread?.quit()
        workerThread = null
    }
}