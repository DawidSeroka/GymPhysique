package com.myproject.gymphysique.core.decoder.flag

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("UnnecessaryAbstractClass")
@Module
@InstallIn(SingletonComponent::class)
abstract class FlagTypeModule {
    @Binds
    abstract fun bindBodyCompositionFlagTypeCheck(
        flagTypeCheckImpl: BodyCompositionFlagTypeCheckImpl
    ): FlagTypeCheck.BodyCompositionFlagTypeCheck
}
