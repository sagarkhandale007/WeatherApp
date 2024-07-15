package com.example.weatherapp.UseCase

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.example.WeatherData
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.viewmodel.MyViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MyViewModel>()

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

                    MyComposable(viewModel = viewModel)

                }
            }
        }
    }
}

data class WeatherDetail(
    val Temp: String,
    val tempMin: String,
    val tempMax: String,
    val pressure: String,
    val humidity: String,
    val tempKf: String,
    val dtTxt: String

)

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp(dataState: WeatherData?, viewModel: MyViewModel) {
    var city by remember { mutableStateOf("Austin") }
    var weatherDetails by remember { mutableStateOf<List<WeatherDetail>>(emptyList()) }


    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color(0xFF0077B6), Color(0xFF00597B)),
        startY = 0f,
        endY = 500f

    )



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = gradientBrush
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Search City Input
            TextField(
                value = city,
                onValueChange = { city = it },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        viewModel.fetchData(city)
                    }
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {


                var data: String? = null
                if (dataState != null) {
                    for (item in dataState.list!!) {
                        val firstItem = dataState.list[0]
                        var Temp: String = firstItem.main?.temp.toString()
                        data = Temp
                    }
                }

                Text(

                    text = dataState?.city?.name.toString(),
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = dataState?.city?.country.toString(),
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                if (data != null) {
                    Text(
                        text = "$data°C",
                        //text = "37.77°C",
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Icon(Icons.Outlined.Star,
                        contentDescription = "",
                        tint = Color.Red,
                    )
                    Text(text = "Clear",
                        color = Color.White,
                    )
                }
            }


            var weather_Data = arrayListOf<WeatherDetail>()

            if (dataState != null) {
                for (item in dataState.list!!) {

                    Log.d("item.main", item.main.toString())

                    var Temp: String = item.main?.temp.toString()
                    var tempMin: String = item.main?.tempMin.toString()
                    var tempMax: String = item.main?.tempMax.toString()
                    var pressure: String = item.main?.pressure.toString()
                    var humidity: String = item.main?.humidity.toString()
                    var tempKf: String = item.main?.tempKf.toString()
                    var dtTxt: String = item.dtTxt.toString()

                    val weatherDetail = WeatherDetail(
                        Temp + "°C",
                        tempMin + "°C",
                        tempMax + "°C",
                        pressure,
                        humidity,
                        tempKf,
                        dtTxt
                    )
                    weather_Data.add(weatherDetail)
                }
            }

            // 3 Hours Climate Details Horizontal Scroll
            if (weather_Data != null) {
                HorizontalScrollableComponent(weather_Data)
            }


            // Weather Details List
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {

                items(weather_Data) { weather_Data ->
                    WeatherListItem(weather_Data)
                }

            }
            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

@Composable
fun WeatherListItem(weather_Data: WeatherDetail) {


    Divider()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            val parts = weather_Data.dtTxt.split(" ")
            val time12 = convertTo12HourFormat(parts[1])
            Text(
                text = parts[0]+" \n"+time12,
                fontSize = 15.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = weather_Data.tempMin+" - "+weather_Data.tempMax,
                fontSize = 15.sp,
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(R.drawable.rainyday),
                contentDescription = null,
//                tint = Color.Blue,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun HorizontalScrollableComponent(weather_Data: List<WeatherDetail>) {

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(weather_Data) { weather_Data ->
            WeatherDetailCard(weather_Data)
        }
    }
    Row(
        modifier = Modifier.padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "5-DAYS FORECAST",
            fontSize = 15.sp,
            modifier = Modifier.weight(1f),
            color = Color.White
        )
    }
}

@Composable
fun WeatherDetailCard(weather_Data: WeatherDetail) {

    Card(
        modifier = Modifier
            .width(100.dp)
            .padding(8.dp),
//             elevation = 4.dp

    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val parts = weather_Data.dtTxt.split(" ")
            val time12 = convertTo12HourFormat(parts[1])

            Text(
                text = time12,
                fontSize = 11.sp,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold


            )
            Spacer(modifier = Modifier.height(8.dp))
            Icon(
                painter = painterResource(R.drawable.sun),
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.size(7.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = weather_Data.Temp,
                fontSize = 12.sp,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold

            )
        }
    }

}


// convert date to 12 Hour Format
fun convertTo12HourFormat(time24: String): String {
    val sdf24 = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val sdf12 = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val date24: Date = sdf24.parse(time24)
    return sdf12.format(date24)
}





@Composable
fun MyComposable(viewModel: MyViewModel) {
    val dataState by viewModel.data.collectAsState()

    // Trigger the API request when the Composable is first displayed
    LaunchedEffect(key1 = true) {

        viewModel.fetchData("Austin")
    }
    Log.d("dataState",dataState.toString())


    WeatherApp(dataState,viewModel)


}

@Composable
fun Greeting3(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    WeatherAppTheme {
        Greeting3("Android")
    }



}