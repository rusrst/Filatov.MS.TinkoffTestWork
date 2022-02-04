package com.example.tinkofftestwork.data.internet

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class InternetRepository{
    private var internetApi: InternetApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://google.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        internetApi = retrofit.create(InternetApi::class.java)
    }
    @Synchronized fun getRequestFromUrl(url: String): String
    {
        val data: Call<String> = internetApi.getData(url)
        val response = data.execute()
        return response.body().toString()
    }

    companion object{
        private var INSTANCE: InternetRepository? = null
        @Synchronized fun initialize(){
            if (INSTANCE == null){
                INSTANCE = InternetRepository()
            }
        }
        fun get(): InternetRepository {
            return INSTANCE ?: throw IllegalStateException("CurrencyInternetRepository = null")
        }
    }
}