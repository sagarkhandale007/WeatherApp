package com.example.weatherapp.viewmodel

import net.bytebuddy.matcher.ElementMatcher

//interface WeatherRepository : MyApiService()


interface WeatherRepository {
    suspend fun fetchWeather(city: String, callback: ElementMatcher.Junction<Any>)
}
