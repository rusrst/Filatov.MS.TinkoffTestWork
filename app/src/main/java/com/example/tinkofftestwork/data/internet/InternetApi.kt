package com.example.tinkofftestwork.data.internet

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface InternetApi {
    @GET
    fun getData(
        @Url url: String,
    ): Call<String>
}