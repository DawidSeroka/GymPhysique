package com.myproject.gymphysique.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.myproject.gymphysique.core.common.dispatcher.Dispatcher
import com.myproject.gymphysique.core.common.dispatcher.GPDispatchers
import com.myproject.gymphysique.core.datastore.UserPreferences
import com.myproject.gymphysique.core.datastore.UserPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideUserPreferencesDataStore(
        @ApplicationContext context: Context,
        @Dispatcher(GPDispatchers.IO) ioDispatcher: CoroutineDispatcher,
        userPreferencesSerializer: UserPreferencesSerializer
    ): DataStore<UserPreferences> =
        DataStoreFactory.create(
            serializer = userPreferencesSerializer,
            scope = CoroutineScope(ioDispatcher + SupervisorJob())
        ) {
            context.dataStoreFile(USER_PREFERENCES_FILE_NAME)
        }

    private const val USER_PREFERENCES_FILE_NAME = "user_preferences.pb"
}
