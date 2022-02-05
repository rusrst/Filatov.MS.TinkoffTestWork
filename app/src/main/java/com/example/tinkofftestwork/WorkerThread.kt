package com.example.tinkofftestwork

import com.example.tinkofftestwork.data.DataClass
import com.example.tinkofftestwork.data.internet.InternetRepository
import com.example.tinkofftestwork.data.json.JsonDataClass
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import androidx.lifecycle.MutableLiveData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


private const val TAG = "BACKGROUND Thread"
private const val MESSAGE_DOWNLOAD_LATEST = 0
private const val MESSAGE_DOWNLOAD_HOT = 1
private const val MESSAGE_DOWNLOAD_TOP = 2
class WorkerThread(private val latestLiveData: MutableLiveData<List<DataClass>?>,
                   private val hotLiveData: MutableLiveData<List<DataClass>?>,
                   private val topLiveData: MutableLiveData<List<DataClass>?>): HandlerThread(TAG) {
    private val internetRepository = InternetRepository.get()
    init {
        start()
        looper
    }
    private var hasQuit = false
    override fun quit(): Boolean {
        hasQuit = true
        return super.quit()
    }
    lateinit var mHandler: Handler
    @Synchronized override fun onLooperPrepared() {
        mHandler = object: Handler(Looper.myLooper()!!){
            override fun handleMessage(msg: Message) =
                loadInternet(msg.obj as String, msg.arg1)

        }
    }

    fun returnData( url: String, id: Int){
        mHandler.obtainMessage(-1, id, 0,  url)
            .sendToTarget()
    }


    fun loadInternet(url: String, type: Int){
        val data:String? = try {
            internetRepository.getRequestFromUrl(url)
        } catch (e: Exception){
            null
        }
        val temp: JsonDataClass?= try{
            Json.decodeFromString<JsonDataClass>(data ?: "")
        }
        catch (e:Exception){
            null
        }
        var result: List<DataClass>? = null
        if (temp != null){
            result = mutableListOf()
            temp.result.forEach {
                result.add(DataClass(it.description, it.gifURL, type))
            }
        }
        when (type){
            MESSAGE_DOWNLOAD_HOT -> hotLiveData.postValue(result)
            MESSAGE_DOWNLOAD_LATEST -> latestLiveData.postValue(result)
            MESSAGE_DOWNLOAD_TOP -> topLiveData.postValue(result)
        }
    }
}

