package com.myproject.gymphysique.core.database

import android.content.Context
import androidx.room.Room
import com.myproject.gymphysique.core.database.DatabaseConstants.DATABASE_NAME
import com.myproject.gymphysique.core.database.dao.MeasurementDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): GpDatabase = Room.databaseBuilder(
        context,
        GpDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun providesMeasurementDao(database: GpDatabase): MeasurementDao {
        return database.measurementDao()
    }
}
