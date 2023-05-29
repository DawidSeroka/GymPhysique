package com.myproject.gymphysqiue.core.domain.decode

import com.myproject.gymphysique.core.common.Result
import com.myproject.gymphysique.core.decoder.ResponseData
import com.myproject.gymphysique.core.model.MeasurementType
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DecodeResponseUseCaseTest {

    private lateinit var decodeResponseUseCase: DecodeResponseUseCase

    @Before
    fun setup() {
        decodeResponseUseCase = DecodeResponseUseCase()
    }

    @Test
    fun `when result is failure, should return emptyList`() {
        val result = decodeResponseUseCase(responseDataFailure)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `when result is loading, should return just Weight`() {
        val result = decodeResponseUseCase(responseDataLoading).first()

        assertEquals(MeasurementType.WEIGHT, result.measurementType)
    }

    @Test
    fun `when result is loading and measurementResult is null, should return just Weight with value 0`() {
        val result = decodeResponseUseCase(responseDataLoading).first()

        assertEquals(MeasurementType.WEIGHT, result.measurementType)
        assertEquals(0.0, result.measurementResult)
    }

    @Test
    fun `when result is success, should return list with size of measurementType values`() {
        val result = decodeResponseUseCase(responseDataSuccess)
        val expectedListSize = MeasurementType.values().size

        assertEquals(expectedListSize, result.size)
    }
}

private val responseDataFailure = Result.error<ResponseData>(Exception("testError"))
private val responseDataLoading =
    Result.loading(
        ResponseData.BodyCompositionResponseData(
            date = "2023-05"
        ) as ResponseData
    )
private val responseDataSuccess =
    Result.success(
        ResponseData.BodyCompositionResponseData(
            date = "2023-05"
        ) as ResponseData
    )
