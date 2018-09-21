package pl.nowicki.openweatherdexprotector

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.CoroutineContext

class WeatherViewModel : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val mutableCity = MutableLiveData<String>().apply { value = "" }
    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
}