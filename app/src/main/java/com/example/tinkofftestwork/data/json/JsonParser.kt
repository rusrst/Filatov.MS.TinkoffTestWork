package com.example.tinkofftestwork.data.json

import com.example.tinkofftestwork.data.DataClass
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class JsonParser {
    companion object{
        fun getList(data: String?, type: Int):List<DataClass>?{
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
            return result
        }
    }
}