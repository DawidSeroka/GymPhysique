package com.myproject.gymphysqiue.core.domain.measure

import com.juul.kable.Advertisement
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ValidateCurrentAdvertisementsUseCaseTest {

    private lateinit var useCase: ValidateCurrentAdvertisementsUseCase
    private val advertisement1: Advertisement by lazy { mockk() }
    private val advertisement2: Advertisement by lazy { mockk() }
    private val advertisement3: Advertisement by lazy { mockk() }

    @Before
    fun setup() {
        useCase = ValidateCurrentAdvertisementsUseCase()
    }

    @Test
    fun `when old list do not contain new Advertisement, should add new Advertisement`() {
        val oldAds = listOf(
            advertisement1,
            advertisement2
        )
        val newAd = advertisement3
        every { advertisement1.address } returns "advertisement1"
        every { advertisement2.address } returns "advertisement2"
        every { advertisement3.address } returns "advertisement3"
        val result = useCase(newAd, oldAds)

        val expectedResult = oldAds + newAd

        assertEquals(expectedResult, result)
    }

    @Test
    fun `when old list contain new Advertisement, should return old list`() {
        val oldAds = listOf(
            advertisement1,
            advertisement2
        )
        val newAd = advertisement3
        every { advertisement1.address } returns "advertisement1"
        every { advertisement2.address } returns "advertisement2"
        every { advertisement3.address } returns "advertisement1"
        val result = useCase(newAd, oldAds)

        assertEquals(oldAds, result)
    }

    @Test
    fun `when old list is empty, should return list with only new advertisement`() {
        val oldAds = emptyList<Advertisement>()
        val newAd = advertisement3
        every { advertisement3.peripheralName } returns "advertisement3"
        val result = useCase(newAd, oldAds)

        assertEquals(listOf(newAd), result)
    }
}
