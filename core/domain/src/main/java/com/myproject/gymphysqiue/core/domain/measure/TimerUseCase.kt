package com.myproject.gymphysqiue.core.domain.measure

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class TimerUseCase @Inject constructor() : suspend (Int) -> Flow<Int> {
    override suspend fun invoke(time: Int): Flow<Int> =
        flow {
            var currentTime = time
            while (currentTime != EndTime) {
                emit(currentTime)
                currentTime -= ReductionValue
                delay(Delay)
            }
        }

    private companion object {
        const val EndTime = 0
        const val ReductionValue = 1
        val Delay = 1000.milliseconds
    }
}