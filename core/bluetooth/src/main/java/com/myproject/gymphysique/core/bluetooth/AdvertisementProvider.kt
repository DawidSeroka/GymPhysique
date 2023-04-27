package com.myproject.gymphysique.core.bluetooth

import com.juul.kable.Advertisement
import kotlinx.coroutines.flow.Flow

interface AdvertisementProvider {
    fun provideAdvertisements(): Flow<Advertisement>
}
