package com.myproject.gymphysique.core

import com.myproject.gymphysique.core.data.MeasurementRepository
import com.myproject.gymphysique.core.data.MeasurementRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMeasurementRepository(
        measurementRepositoryImpl: MeasurementRepositoryImpl
    ): MeasurementRepository

}