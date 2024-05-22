package com.myproject.gymphysqiue.core.domain.charts

import com.myproject.gymphysique.core.data.MeasurementRepository
import com.myproject.gymphysique.core.model.Measurement
import com.myproject.gymphysique.core.model.MeasurementType
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ObserveMeasurementsUseCase @Inject constructor(
    private val measurementRepository: MeasurementRepository
) : (String?, MeasurementType) -> Flow<List<Measurement>> {
    override fun invoke(dateParam: String?, measurementType: MeasurementType): Flow<List<Measurement>> {
        dateParam?.let {
            return measurementRepository.observeMeasurements(it, measurementType)
        } ?: kotlin.run {
            val formatter = DateTimeFormatter.ofPattern(FORMAT_PATTERN)
                .withZone(ZoneId.systemDefault())
            val currentDate = formatter.format(Instant.now())
            return measurementRepository.observeMeasurements(currentDate, measurementType)
        }
    }

    private companion object{
        const val FORMAT_PATTERN = "yyyy-MM"
    }
}
