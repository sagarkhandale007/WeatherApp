package com.example.weatherapp.viewmodel

import com.example.example.WeatherData
import net.bytebuddy.matcher.ElementMatchers.any
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {

    @Mock
    private lateinit var weatherRepository: WeatherRepository

    private lateinit var viewModel: MyViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MyViewModel()
    }

    @Test
    suspend fun testFetchWeatherDataSuccess() {
        // Define a city and a sample WeatherData object
        val city = "Austin"
        val weatherData = WeatherData(/* Initialize with sample data */)

        // Mock the behavior of the weatherRepository when fetchWeather is called
        Mockito.`when`(weatherRepository.fetchWeather(city, any())).thenAnswer {
            val callback = it.arguments[1] as (Result<WeatherData>) -> Unit
            callback(Result.success(weatherData)
            )

        }


        // Call the function being tested
        viewModel.fetchData(city)

        // Assert that the ViewModel behaves as expected when data is successfully fetched
        // You can check LiveData or StateFlow values or use other assertions here
    }

    @Test
    suspend fun testFetchWeatherDataError() {
        // Define a city
        val city = "InvalidCity"

        // Mock the behavior of the weatherRepository when fetchWeather is called
        Mockito.`when`(weatherRepository.fetchWeather(city, any())).thenAnswer {
            val callback = it.arguments[1] as (Result<WeatherData>) -> Unit
            callback(Result.failure(Throwable()))
        }

        // Call the function being tested
        viewModel.fetchData(city)

        // Assert that the ViewModel behaves as expected when there's an error
        // You can check LiveData or StateFlow values or use other assertions here
    }
}
