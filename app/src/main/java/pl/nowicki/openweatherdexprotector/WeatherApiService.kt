package pl.nowicki.openweatherdexprotector

import pl.nowicki.openweatherdexprotector.model.WeatherRsp
import retrofit2.Call
import retrofit2.http.GET

interface WeatherApiService {

    @GET("/data/2.5/forecast?q=Katowice&APPID=049cc7883268b2ac341d14f127461559")
    fun forecast(): Call<WeatherRsp>
}