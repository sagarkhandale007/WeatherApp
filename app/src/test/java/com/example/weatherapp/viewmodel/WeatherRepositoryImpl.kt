package com.example.weatherapp.viewmodel

import android.util.Log
import com.example.example.WeatherData
import com.example.weatherapp.Repository.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.bytebuddy.matcher.ElementMatcher
import retrofit2.Response


class WeatherRepositoryImpl(private val apiService: ApiService) : WeatherRepository {

    override suspend fun fetchWeather(city: String, callback: ElementMatcher.Junction<Any>) {
        // Make the actual API request using apiService
        // Handle success and error cases, and invoke the callback accordingly

         val myApi = ApiService.create() // Replace with your Retrofit API service
         val _data = MutableStateFlow<WeatherData?>(null)
         val data = _data.asStateFlow()


        var city = "Austin"
        var cnt = "48"
        var appid = "cd4cd7afbc1a41867e75a59ae130c921"
        var units = "metric"

                try {
                    val response: Response<WeatherData> = myApi.getData(city,cnt,appid,units)
                    if (response.code() == 200) { // Check if the HTTP status code is 200 (OK)
                        _data.value = response.body()
                        _data.value?.list

                    } else {
                        // Handle other HTTP status codes (e.g., error responses)
                        Log.d("data failed","data failed")
                    }
                } catch (e: Exception) {
                    Log.d("data failed",e.toString())

                }




    }
}
