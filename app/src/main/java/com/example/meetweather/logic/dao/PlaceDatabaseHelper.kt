package com.example.meetweather.logic.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PlaceDatabaseHelper(val context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {

    private val createPlace = "create table place(" +
            " id integer primary key autoincrement," +
            " name text," +
            " lng text," +
            " lat text," +
            " address text)"

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(createPlace)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}