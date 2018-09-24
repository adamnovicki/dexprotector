package pl.nowicki.openweatherdexprotector

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initModel()

        button.setOnClickListener {
            viewModel.action()
        }
    }

    fun initModel() {
        Timber.d("initModel")
        viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        viewModel.city.observe {

        }
        viewModel.temperature.observe {
            tv.text = it.toString()
        }
    }

    private fun <T> LiveData<T>.observe(observe: (T?) -> Unit) = observe(this@MainActivity, Observer {
        Timber.d("onChanged $it")
        observe(it)
    })

}
