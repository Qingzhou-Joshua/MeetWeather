package com.example.meetweather.logic

import androidx.lifecycle.liveData
import com.example.meetweather.logic.dao.PlaceDBUtil
import com.example.meetweather.logic.model.Place
import com.example.meetweather.logic.model.Weather
import com.example.meetweather.logic.network.MeetWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository {

    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val placeResponse = MeetWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                MeetWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                MeetWeatherNetwork.getDailyWeather(lng, lat)
            }
            val deferredHour = async {
                MeetWeatherNetwork.getHourWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            val hourResponse = deferredHour.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok"
                && hourResponse.status == "ok") {
                val weather = Weather(realtimeResponse.result.realtime,
                    dailyResponse.result.daily, hourResponse.result.hourly)
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException("realtime status is ${realtimeResponse.status}" +
                        "daily status is ${dailyResponse.status}" +
                        "hour status is ${hourResponse.status}")
                )
            }
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context = context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
    }

    fun savedPlace(place: Place) = PlaceDBUtil.savePlace(place)

    fun getSavedPlace() = PlaceDBUtil.getSavedPlace()[0]

    fun isPlaceSaved() = PlaceDBUtil.isPlaceSaved()
}