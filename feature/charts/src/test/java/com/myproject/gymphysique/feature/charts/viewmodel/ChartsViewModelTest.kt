package com.myproject.gymphysique.feature.charts.viewmodel

import com.myproject.gymphysique.core.common.toDate
import com.myproject.gymphysique.core.model.Measurement
import com.myproject.gymphysique.core.model.MeasurementType
import com.myproject.gymphysique.core.testing.FakeMeasurementRepository
import com.myproject.gymphysique.core.testing.util.MainDispatcherRule
import com.myproject.gymphysique.feature.charts.viewModel.ChartsViewModel
import com.myproject.gymphysqiue.core.domain.charts.GetMeasurementsUseCase
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class ChartsViewModelTest {

    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val measurementRepository = FakeMeasurementRepository()
    private val getMeasurementUseCase by lazy {
        spyk(GetMeasurementsUseCase(measurementRepository))
    }

    private lateinit var viewModel: ChartsViewModel

    @Before
    fun setup() {
        viewModel = ChartsViewModel(getMeasurementUseCase)
    }

    @Suppress("MaxLineLength")
    @Test
    fun `when onMeasurementTypeSelected, measurements with dateParam exists, then date and measurementType and measurements are updated `() =
        runTest {
            Constants.measurements.forEach { measurementRepository.saveMeasurement(it) }
            val date = "2022-05"
            viewModel.onDateSelected(date = date.toDate())
            viewModel.onMeasurementTypeSelected(measurementType = MeasurementType.WEIGHT)

            val expectedMeasurements = Constants.measurements.filter { it.date.equals(date) }
            val expectedMeasurementType = MeasurementType.WEIGHT
            val expectedDate = date

            assertEquals(expectedMeasurementType, viewModel.state.value.selectedMeasurementType)
            assertEquals(expectedDate, viewModel.state.value.selectedDate)
            assertEquals(expectedMeasurements, viewModel.state.value.measurements)
        }

    @Suppress("MaxLineLength")
    @Test
    fun `when onMeasurementTypeSelected, measurements with dateParam non exists, then date and measurementType are updated and measurements are empty `() =
        runTest {
            Constants.measurements.forEach { measurementRepository.saveMeasurement(it) }
            val date = "2022-09"
            viewModel.onDateSelected(date = date.toDate())
            viewModel.onMeasurementTypeSelected(measurementType = MeasurementType.WEIGHT)

            val expectedMeasurements = emptyList<Measurement>()
            val expectedMeasurementType = MeasurementType.WEIGHT
            val expectedDate = date

            assertEquals(expectedMeasurementType, viewModel.state.value.selectedMeasurementType)
            assertEquals(expectedDate, viewModel.state.value.selectedDate)
            assertEquals(expectedMeasurements, viewModel.state.value.measurements)
        }

    @Test
    fun `when onMeasurementTypeDropdownSelected, dropdownMeasurementTypeExpanded = false, then dropdownMeasurementTypeExpanded = true`() {
        viewModel.onMeasurementTypeDropdownSelected()

        assertEquals(true, viewModel.state.value.dropdownMeasurementTypeExpanded)
    }

    @Test
    fun `when onDateDropdownSelected, dropdownDateExpanded = false, then dropdownDateExpanded = true`() {
        viewModel.onDateDropdownSelected()

        assertEquals(true, viewModel.state.value.dropdownDateExpanded)
    }
}

private object Constants {
    val measurements = listOf(
        Measurement(
            id = 0,
            measurementResult = 1.0,
            date = "2022-05",
            measurementType = MeasurementType.WEIGHT
        ),
        Measurement(
            id = 1,
            measurementResult = 1.0,
            date = "2022-06",
            measurementType = MeasurementType.BMI
        ),
        Measurement(
            id = 2,
            measurementResult = 1.0,
            date = "2022-07",
            measurementType = MeasurementType.BASAL_METABOLISM
        )
    )
}
