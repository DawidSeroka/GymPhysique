package com.myproject.gymphysique.core.decoder.flag

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface FlagTypeModule {
    @Binds
    abstract fun bindBodyCompositionFlagTypeCheck(
        flagTypeCheckImpl: BodyCompositionFlagTypeCheckImpl
    ): FlagTypeCheck.BodyCompositionFlagTypeCheck
}