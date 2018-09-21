package pl.nowicki.openweatherdexprotector

import pl.nowicki.openweatherdexprotector.model.WeatherRsp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("/data/2.5/forecast?APPID=049cc7883268b2ac341d14f127461559")
    fun forecast(@Query("q") city: String): Call<WeatherRsp>
}