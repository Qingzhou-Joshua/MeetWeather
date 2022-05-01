package com.example.meetweather.logic.dao

import android.annotation.SuppressLint
import android.content.ContentValues
import com.example.meetweather.MeetWeatherApplication
import com.example.meetweather.logic.model.Location
import com.example.meetweather.logic.model.Place

object PlaceDBUtil {
    @SuppressLint("StaticFieldLeak")
    val dbHelper = PlaceDatabaseHelper(MeetWeatherApplication.context, "Place.db", 1)

    fun savePlace(place: Place) {
        val db = dbHelper.writableDatabase
        val value = ContentValues().apply {
            put("name", place.name)
            put("address", place.address)
            put("lng", place.location.lng)
            put("lat", place.location.lat)
        }
        db.insert("Place", null, value)
    }

    @SuppressLint("Range")
    fun getSavedPlace(): List<Place> {
        val result = ArrayList<Place>()
        val db = dbHelper.writableDatabase
        val cursor = db.query("Place", null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val address = cursor.getString(cursor.getColumnIndex("address"))
                val lng = cursor.getString(cursor.getColumnIndex("lng"))
                val lat = cursor.getString(cursor.getColumnIndex("lat"))
                val location = Location(lng, lat)
                val place = Place(name, location, address)
                result.add(place)
            } while(cursor.moveToNext())
        }
        cursor.close()
        return result
    }

    fun isPlaceSaved() = getSavedPlace().isNotEmpty()

    fun deletePlace(name: String) {
        val db = dbHelper.writableDatabase
        db.delete("Place", "name=?", arrayOf(name))
    }
}