package com.myproject.gymphysqiue.core.domain

import com.juul.kable.Advertisement
import com.myproject.gymphysique.core.bluetooth.DeviceScanner
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProvideAdvertisementsUseCase @Inject constructor(
    private val deviceScanner: DeviceScanner
): () -> Flow<Advertisement> {
    override fun invoke(): Flow<Advertisement> = deviceScanner.provideAdvertisements()
}