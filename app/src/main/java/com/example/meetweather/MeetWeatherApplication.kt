package com.example.meetweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class MeetWeatherApplication : Application() {

    companion object {

        const val TOKEN = "sr3kTZFjU5jb7fno"

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}