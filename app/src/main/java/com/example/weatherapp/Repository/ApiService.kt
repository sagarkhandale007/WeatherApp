package com.example.weatherapp.Repository

import com.example.example.WeatherData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("data/2.5/forecast?")
    suspend fun getData(@Query("q") q: String?,
                        @Query("cnt") cnt: String?,
                        @Query("appid") appid: String?,
                        @Query("units") units: String?): Response<WeatherData>

    companion object {
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
