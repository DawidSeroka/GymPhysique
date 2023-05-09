package com.myproject.gymphysique.core.testing.di

import com.myproject.gymphysique.core.common.dispatcher.Dispatcher
import com.myproject.gymphysique.core.common.dispatcher.GPDispatchers
import com.myproject.gymphysique.core.common.dispatcher.di.DispatchersModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DispatchersModule::class]
)
object TestDispatchersModule {
    @Provides
    @Dispatcher(GPDispatchers.IO)
    fun providesIODispatcher(testDispatcher: TestDispatcher): CoroutineDispatcher = testDispatcher
}
