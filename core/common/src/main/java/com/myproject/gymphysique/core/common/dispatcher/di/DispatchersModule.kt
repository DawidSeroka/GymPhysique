package com.myproject.gymphysique.core.common.dispatcher.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    @Dispatcher(GPDispatchers.IO)
    fun provideIODispatcher(): CoroutineDispatcher =
        Dispatchers.IO
}
