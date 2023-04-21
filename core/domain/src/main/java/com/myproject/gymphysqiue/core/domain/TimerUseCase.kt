package com.myproject.gymphysqiue.core.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TimerUseCase @Inject constructor(): suspend (Int) -> Flow<Int> {
    override suspend fun invoke(time: Int): Flow<Int> {
        return flow {
            var currentTime = time
            while (currentTime != 0) {
                emit(currentTime)
                currentTime -= 1
                delay(1_000)
            }
        }
    }
}