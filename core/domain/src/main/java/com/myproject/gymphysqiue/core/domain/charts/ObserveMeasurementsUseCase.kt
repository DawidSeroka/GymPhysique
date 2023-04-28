package com.myproject.gymphysqiue.core.domain.charts

import com.myproject.gymphysique.core.data.MeasurementRepository
import com.myproject.gymphysique.core.model.Measurement
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ObserveMeasurementsUseCase @Inject constructor(
    private val measurementRepository: MeasurementRepository
) : (String?) -> Flow<List<Measurement>> {
    override fun invoke(dateParam: String?): Flow<List<Measurement>> {
        dateParam?.let {
            return measurementRepository.getMeasurementsWithDate(it)
        } ?: kotlin.run {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM")
                .withZone(ZoneId.systemDefault())
            val currentDate = formatter.format(Instant.now())
            return measurementRepository.getMeasurementsWithDate(currentDate)
        }
    }
}