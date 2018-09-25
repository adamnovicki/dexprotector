package pl.nowicki.openweatherdexprotector

import kotlinx.coroutines.experimental.CompletableDeferred

sealed class Action {
    data class Spin(val id: Int) : Action()
    data class Done(val ack: CompletableDeferred<Boolean>) : Action()
}