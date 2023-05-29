package com.myproject.gymphysqiue.core.domain.charts

import app.cash.turbine.test
import com.myproject.gymphysique.core.model.Measurement
import com.myproject.gymphysique.core.model.MeasurementType
import com.myproject.gymphysique.core.testing.repository.FakeMeasurementRepository
import com.myproject.gymphysique.core.testing.util.MainDispatcherRule
import com.myproject.gymphysqiue.core.domain.charts.Constants.mockCorrectMeasurementType
import com.myproject.gymphysqiue.core.domain.charts.Constants.mockCurrentDate
import com.myproject.gymphysqiue.core.domain.charts.Constants.mockIncorrectDate
import com.myproject.gymphysqiue.core.domain.charts.Constants.mockIncorrectMeasurementType
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

internal class ObserveMeasurementsUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val measurementRepository by lazy { FakeMeasurementRepository() }
    private lateinit var observeMeasurementsUseCase: ObserveMeasurementsUseCase

    @Before
    fun setup() {
        observeMeasurementsUseCase = ObserveMeasurementsUseCase(measurementRepository)
    }

    @Test
    fun `when dateParam and measurementType are correct, should return flow List Measurement`() =
        runTest {
            mockMeasurements.forEach { measurementRepository.saveMeasurement(it) }
            observeMeasurementsUseCase(mockCurrentDate, mockCorrectMeasurementType).test {
                val result: List<Measurement> = awaitItem()

                assertTrue(mockMeasurements.containsAll(result))
            }
        }

    @Test
    fun `when dateParam is incorrect and measurementType is correct, should return emptyFlow`() =
        runTest {
            mockMeasurements.forEach { measurementRepository.saveMeasurement(it) }
            observeMeasurementsUseCase(mockIncorrectDate, mockCorrectMeasurementType).test {
                expectNoEvents()
            }
        }

    @Test
    fun `when dateParam is correct and measurementType is not correct, should return emptyFlow`() =
        runTest {
            mockMeasurements.forEach { measurementRepository.saveMeasurement(it) }
            observeMeasurementsUseCase(mockCurrentDate, mockIncorrectMeasurementType).test {
                expectNoEvents()
            }
        }

    @Test
    fun `when dateParam is not correct and measurementType is not correct, should return emptyFlow`() =
        runTest {
            mockMeasurements.forEach { measurementRepository.saveMeasurement(it) }
            observeMeasurementsUseCase(mockIncorrectDate, mockIncorrectMeasurementType).test {
                expectNoEvents()
            }
        }
}

private object Constants {
    const val mockCurrentDate = "2023-05"
    const val mockIncorrectDate = "2013-09"
    val mockCorrectMeasurementType = MeasurementType.WEIGHT
    val mockIncorrectMeasurementType = MeasurementType.BODY_FAT
}

private val mockMeasurements = listOf(
    Measurement(0, 14.0, date = "2023-05-24", measurementType = MeasurementType.WEIGHT),
    Measurement(1, 15.0, date = "2023-05-23", measurementType = MeasurementType.WEIGHT),
    Measurement(2, 16.0, date = "2023-04-24", measurementType = MeasurementType.WEIGHT),
    Measurement(3, 17.0, date = "2023-06-24", measurementType = MeasurementType.BMI)
)
