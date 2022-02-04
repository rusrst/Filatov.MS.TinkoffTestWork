package com.example.tinkofftestwork

import com.example.tinkofftestwork.data.DataClass
import com.example.tinkofftestwork.data.internet.InternetRepository
import com.example.tinkofftestwork.data.json.JsonDataClass

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


private const val TAG = "BACKGROUND Thread"
private const val MESSAGE_DOWNLOAD_LATEST = 0
class WorkerThread(private val latestLiveData: MutableLiveData<List<DataClass>?>): HandlerThread(TAG) {
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
    override fun onLooperPrepared() {
        mHandler = object: Handler(Looper.myLooper()!!){
            override fun handleMessage(msg: Message) {
                when (msg.what){
                    MESSAGE_DOWNLOAD_LATEST -> loadInternet(msg.obj as String, msg.arg1)
                }
            }
        }
    }

    fun returnData( url: String, id: Int){
        when (id){
            MESSAGE_DOWNLOAD_LATEST -> mHandler.obtainMessage(MESSAGE_DOWNLOAD_LATEST, id, 0,  url)
                .sendToTarget()
        }

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
        latestLiveData.postValue(result)
    }
}

