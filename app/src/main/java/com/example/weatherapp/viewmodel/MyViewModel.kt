package com.example.weatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.example.WeatherData
import com.example.weatherapp.Repository.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MyViewModel : ViewModel() {
    private val myApi = ApiService.create()
    private val _data = MutableStateFlow<WeatherData?>(null)
    val data = _data.asStateFlow()


    fun fetchData(city: String) {
        val cnt = "48"
        val appid = "cd4cd7afbc1a41867e75a59ae130c921"
        val units = "metric"

        viewModelScope.launch {
            try {
                val response: Response<WeatherData> = myApi.getData(city,cnt,appid,units)
                if (response.code() == 200) { // Check if the HTTP status code is 200 (OK)
                    _data.value = response.body()
                    _data.value?.list
                } else {
                    Log.d("data failed","data failed")
                }
            } catch (e: Exception) {
                Log.d("data failed",e.toString())
            }
        }
    }
}
