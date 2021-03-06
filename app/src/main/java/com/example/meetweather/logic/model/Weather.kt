package com.example.meetweather.logic.model

data class Weather(
    val realtime: RealtimeResponse.Realtime,
    val daily: DailyResponse.Daily,
    val hour: HourResponse.Hourly
)
