package Hackiton.service

import Hackiton.models.weather.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    //@GET("/data/2.5/weather?id=2158177&units=metric&appid=2d6333bd76970c4b1d16306b517f334c")
    @GET("/data/2.5/weather")
    suspend fun getWeather(@Query("id") cityId: String,
                           @Query("units") units: String,
                           @Query("appid") apiKey: String): Response<WeatherModel>

}