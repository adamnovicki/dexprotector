package pl.nowicki.openweatherdexprotector

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_actor.*
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.android.Main
import kotlinx.coroutines.experimental.channels.SendChannel
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch
import timber.log.Timber
import kotlin.coroutines.experimental.CoroutineContext

class ActorActivity : AppCompatActivity(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actor)

        buttonActor.setOnClickListener {

            val job = launch(Dispatchers.Main) {
                val start = System.currentTimeMillis()

                val ack = CompletableDeferred<Boolean>()
                (1..1001).map { if (it == 1001) Action.Done(ack) else Action.Spin(it) }
                        .forEach { action ->
                            launch(Dispatchers.Default) {
                                actor.send(action)
                                val duration = System.currentTimeMillis() - start
                                Timber.d("Thread: ${Thread.currentThread().name} id: ${it.id} duration: $duration")
                            }
                        }
                ack.await()
                Timber.d("time: ${System.currentTimeMillis() - start}")
            }
        }
    }

    private val children: List<SendChannel<Action>> = (1..4).map { createSpinActor(it) }

    private var next = 0

    private val actor = actor<Action>(Dispatchers.Main) {
        consumeEach { action ->
            when (action) {
                is Action.Spin -> children[next++ % children.size].send(action)
                is Action.Done -> {
                    val acks = (1..children.size).map { CompletableDeferred<Boolean>() }
                    acks
                            .withIndex()
                            .forEach { (index, ack) ->
                                children[index].send(Action.Done(ack))
                            }
                    acks.map { it.await() }
                    action.ack.complete(true)
                }
            }
        }
    }

    fun createSpinActor(it: Int) = actor<Action>(Dispatchers.Main, 0) {
        consumeEach { action ->
            when (action) {
                is Action.Spin -> spin(action.id)
                is Action.Done -> action.ack.complete(true)
            }
        }
    }

    private fun spin(value: Int, durationMillis: Int = 10): Int {
        val startMillis = System.currentTimeMillis()
        while (System.currentTimeMillis() - startMillis < durationMillis) {

        }
        return value
    }

    fun initModel() {
        Timber.d("initModel")

    }

}
