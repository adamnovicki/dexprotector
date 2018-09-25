package pl.nowicki.openweatherdexprotector

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.android.Main
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.actor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.nowicki.openweatherdexprotector.model.WeatherRsp
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit
import kotlin.coroutines.experimental.CoroutineContext

class WeatherViewModel : ViewModel(), CoroutineScope {

    val SERVER_URL = "https://api.openweathermap.org"

    val cities: MutableList<String> = mutableListOf("Katowice", "London", "Ulcinj")
    var cityCounter = 0

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val city = MutableLiveData<String>().apply { value = "" }
    val loading = MutableLiveData<Boolean>().apply { value = false }
    val temperature = MutableLiveData<Double>().apply { value = 0.0 }

    private val actor = actor<String>(Dispatchers.Main, Channel.CONFLATED) {
        Timber.d("SelectCity")
        for (selected in this) {
            city.value = selected
            loading.value = true
            temperature.value = getTemperature(selected)
            loading.value = false
        }
    }

    init {
        Timber.d("init")
        //action()
    }

    fun action() = actor.offer(cities.get(++cityCounter % 3))

    override fun onCleared() {
        actor.close()
    }

    private suspend fun getTemperature(city: String) : Double {
        Timber.d("getTemperature")
        val weather = getDailyForecastsByCity(city)
        return weather.list!![0].main.temp
    }

    private suspend fun getDailyForecastsByCity(city: String) : WeatherRsp {

//        return suspendCoroutine {
//            val weatherCall = getForecast(getForecastService(), city)
//            weatherCall.enqueue(object : Callback<WeatherRsp> {
//                override fun onFailure(call: Call<WeatherRsp>?, t: Throwable?) {
//                    Timber.e("onFailure")
//                }
//
//                override fun onResponse(call: Call<WeatherRsp>?, response: Response<WeatherRsp>?) {
//                    val sb = StringBuilder(response?.body()?.city!!.name.toString())
//                    sb.append(" ")
//                    sb.append(response.body()?.list!![0].main.temp.toString())
//                    Timber.e("sb: $sb")
//                }
//            })
//
//        }

        return getForecast(getForecastService(), cities.get(++cityCounter % 3)).await()
    }


    private fun getForecastService(): WeatherApiService {

        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)

        val okBuilder = OkHttpClient.Builder()
        okBuilder.cookieJar(JavaNetCookieJar(cookieManager))

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        okBuilder.addNetworkInterceptor(logging)
        okBuilder.readTimeout(60*1000, TimeUnit.MILLISECONDS)
        okBuilder.connectTimeout(60*1000, TimeUnit.MILLISECONDS)

        val retrofit = Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .client(okBuilder.build())
                .build()

        return retrofit.create(WeatherApiService::class.java)
    }

    private fun getForecast(weatherService: WeatherApiService, city: String): Call<WeatherRsp> {
        return weatherService.forecast(city)
    }
}