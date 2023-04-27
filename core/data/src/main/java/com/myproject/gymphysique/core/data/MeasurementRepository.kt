package com.myproject.gymphysique.core.data

import com.myproject.gymphysique.core.database.dao.MeasurementDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MeasurementRepository @Inject constructor(
    private val measurementDao: MeasurementDao
)
