package com.example.tinkofftestwork.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tinkofftestwork.WorkerThread
import com.example.tinkofftestwork.data.DataClass

class SharedViewModel: ViewModel() {
    var workerThread: WorkerThread? = null
    var latestDataLiveData = MutableLiveData<List<DataClass>?>()
    var latestData = mutableListOf<DataClass>()
    val latestSimpleClass = SimpleClass()


    var hotDataLiveData = MutableLiveData<List<DataClass>?>()
    var hotData = mutableListOf<DataClass>()
    val hotSimpleClass = SimpleClass()



    var topDataLiveData = MutableLiveData<List<DataClass>?>()
    var topData = mutableListOf<DataClass>()
    val topSimpleClass = SimpleClass()

    override fun onCleared() {
        super.onCleared()
        workerThread?.quit()
        workerThread = null
    }
}

data class SimpleClass(var startDownload:Boolean = false, var currentCounter: Int? = null, var currentPageCounter: Int? = null)