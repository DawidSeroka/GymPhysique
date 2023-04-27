package com.myproject.gymphysqiue.core.domain.measure

import com.juul.kable.State
import com.myproject.gymphysique.core.model.ConnectionState
import javax.inject.Inject

class ObserveConnectStateUseCase @Inject constructor() :
    suspend (State) -> ConnectionState {
    override suspend fun invoke(
        connectState: State
    ): ConnectionState {
        return when (connectState) {
            State.Connected ->
                ConnectionState.CONNECTED
            is State.Connecting ->
                ConnectionState.CONNECTING
            is State.Disconnected ->
                // onStopMeasureClick()
                ConnectionState.DISCONNECTED
            else ->
                ConnectionState.CONNECTING
        }
    }
}
