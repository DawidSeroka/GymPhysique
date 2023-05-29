package com.myproject.gymphysique.feature.measure.viewmodel

import com.juul.kable.Advertisement
import com.myproject.gymphysique.core.testing.FakeMeasurementRepository
import com.myproject.gymphysique.core.testing.util.MainDispatcherRule
import com.myproject.gymphysique.feature.measure.AdvertisingStatus
import com.myproject.gymphysqiue.core.domain.decode.DecodeDataUseCase
import com.myproject.gymphysqiue.core.domain.measure.AddMeasurementUseCase
import com.myproject.gymphysqiue.core.domain.measure.ObserveConnectStateUseCase
import com.myproject.gymphysqiue.core.domain.measure.ProvideAdvertisementsUseCase
import com.myproject.gymphysqiue.core.domain.measure.TimerUseCase
import com.myproject.gymphysqiue.core.domain.measure.ValidateCurrentAdvertisementsUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue


class MeasureViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val measurementRepository = FakeMeasurementRepository()
    private val provideAdvertisementsUseCase: ProvideAdvertisementsUseCase by lazy { mockk() }
    private val observeConnectStateUseCase: ObserveConnectStateUseCase by lazy {
        ObserveConnectStateUseCase()
    }
    private val validateCurrentAdvertisementsUseCase: ValidateCurrentAdvertisementsUseCase by lazy {
        ValidateCurrentAdvertisementsUseCase()
    }
    private val timerUseCase: TimerUseCase by lazy {
        TimerUseCase()
    }
    private val decodeDataUseCase: DecodeDataUseCase by lazy { mockk() }
    private val addMeasurementUseCase: AddMeasurementUseCase by lazy {
        AddMeasurementUseCase(measurementRepository)
    }
    private lateinit var viewModel: MeasureViewModel

    @Before
    fun setup(){
        viewModel = MeasureViewModel(
            provideAdvertisementsUseCase,
            observeConnectStateUseCase,
            validateCurrentAdvertisementsUseCase,
            timerUseCase,
            decodeDataUseCase,
            addMeasurementUseCase
        )
    }

    @Test
    fun `when onSearchDevicesClick, appropiate devices exists, then advertisements are added after scanTime`() = runTest {
        coEvery { provideAdvertisementsUseCase() } coAnswers {
            flow {
                var counter = 0
                while (true) {
                    val advertisement: Advertisement = mockk()
                    every { advertisement.uuids } returns listOf(UUID.fromString(Constants.appropiateServiceUuid))
                    every { advertisement.address } returns "Address $counter"
                    emit(advertisement)
                    delay(1000)
                    counter++
                }
            }
        }
        viewModel.onSearchDevicesClick()

        assert(viewModel.state.value.advertisingStatus == AdvertisingStatus.ADVERTISING,)
        delay(10000)
        assert(viewModel.state.value.advertisingStatus == AdvertisingStatus.STOPPED,)
        assert(viewModel.state.value.advertisements.isNotEmpty())
    }

    @Test
    fun `when onSearchDevicesClick, appropiate devices non exists, then advertisements are empty after scanTime`() = runTest {
        coEvery { provideAdvertisementsUseCase() } coAnswers {
            emptyFlow()
        }
        viewModel.onSearchDevicesClick()

        assert(viewModel.state.value.advertisingStatus == AdvertisingStatus.ADVERTISING,)
        delay(10000)
        assert(viewModel.state.value.advertisingStatus == AdvertisingStatus.STOPPED,)
        assert(viewModel.state.value.advertisements.isEmpty())
    }

    @Test
    fun `when onDisconnectClick, measureState = true, then measureState = false`() {
        viewModel.onDisconnectClick()

        assertTrue(!viewModel.state.value.measureState)
    }

    @Test
    fun `when onStopMeasureClick, then measureState = false`() {
        viewModel.onStopMeasureClick()

        assertTrue(!viewModel.state.value.measureState)
    }

    @Test
    fun `when onSaveMeasurementResultReset, then saveMeasurementResult = null`() {
        viewModel.onSaveMeasurementClick()

        assertNull(viewModel.state.value.saveMeasurementResult)
    }

}

private object Constants{
    const val appropiateServiceUuid = "0000181b-0000-1000-8000-00805f9b34fb"
}
