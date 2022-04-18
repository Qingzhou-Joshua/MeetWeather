package com.example.meetweather.ui.weather

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meetweather.R
import com.example.meetweather.logic.model.HourResponse.*
import com.example.meetweather.logic.model.getSky

class HourAdapter(private val tempList: List<Temperature>, private val skyList: List<Skycon>) :
    RecyclerView.Adapter<HourAdapter.ViewHolder>(){

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val time: TextView = view.findViewById(R.id.time)
        val weatherIcon: ImageView = view.findViewById(R.id.hourWeather)
        val temperatureText: TextView = view.findViewById(R.id.hourTemp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hour_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val temp = tempList[position]
        val sky = skyList[position]
        val timeStr = sky.datetime.split("T")[1].split("+")[0]
        holder.time.text = timeStr
        holder.weatherIcon.setImageResource(getSky(sky.value).icon)
        holder.temperatureText.text = "${temp.value.toInt()}℃"
    }

    override fun getItemCount() = tempList.size

}