package com.example.tinkofftestwork

import android.app.Application
import com.bumptech.glide.Glide
import com.example.tinkofftestwork.data.internet.InternetRepository


class MyApp: Application() {
    override fun onCreate() {
        InternetRepository.initialize()
        super.onCreate()
    }
}