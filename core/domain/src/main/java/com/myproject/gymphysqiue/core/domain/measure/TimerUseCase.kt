package com.myproject.gymphysqiue.core.domain.measure

import com.myproject.gymphysqiue.core.domain.measure.Constants.delay
import com.myproject.gymphysqiue.core.domain.measure.Constants.endTime
import com.myproject.gymphysqiue.core.domain.measure.Constants.reductionValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class TimerUseCase @Inject constructor() : suspend (Int) -> Flow<Int> {
    override suspend fun invoke(time: Int): Flow<Int> {
        return flow {
            var currentTime = time
            while (currentTime != endTime) {
                emit(currentTime)
                currentTime -= reductionValue
                delay(delay)
            }
        }
    }
}

private object Constants {
    const val endTime = 0
    const val reductionValue = 1
    val delay = 1000.milliseconds
}
