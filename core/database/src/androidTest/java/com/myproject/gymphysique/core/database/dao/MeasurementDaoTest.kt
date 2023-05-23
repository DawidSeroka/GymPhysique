package com.myproject.gymphysique.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.myproject.gymphysique.core.database.GpDatabase
import com.myproject.gymphysique.core.database.dao.Constant.measurements
import com.myproject.gymphysique.core.database.model.MeasurementEntity
import com.myproject.gymphysique.core.model.MeasurementType
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException
import kotlin.test.assertEquals

internal class MeasurementDaoTest {

    private lateinit var dao: MeasurementDao
    private lateinit var db: GpDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            GpDatabase::class.java
        ).build()
        dao = db.measurementDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertMeasurement_shouldReturnCorrectId() = runTest {
        val measurement = measurements[0]
        val result = dao.insertMeasurement(measurement)

        assert(result.toInt() == 1)
    }

    @Test
    fun insertMeasurement_insertMeasurementsWithSameId_shouldReplaceAndStoreLastMeasurement() =
        runTest {
            measurements.forEach { dao.insertMeasurement(it) }
            val result = dao.getMeasurements("2022-05", MeasurementType.WEIGHT)
                .find { it.id == 1 }

            assert(result?.measurementResult == 4.0)
        }

    @Test
    fun observeMeasurements_correctDateAndMeasurementExists_shouldReturnMeasurements() =
        runTest {
            val dateParam = "2022-05"
            measurements.forEach { dao.insertMeasurement(it) }
            dao.observeMeasurements(dateParam, MeasurementType.WEIGHT).test {
                val result = awaitItem().size
                // -1 because there is two measurements with same id, so it is replaced
                val expectedResult = measurements
                    .filter {
                        it.measurementType == MeasurementType.WEIGHT && it.date.contains(
                            dateParam
                        )
                    }.size - 1

                assertEquals(expectedResult, result)
            }
        }

    @Test
    fun observeMeasurements_inCorrectDateAndMeasurementExists_shouldReturnEmptyFlow() =
        runTest {
            measurements.forEach { dao.insertMeasurement(it) }
            val dateParam = "2022-04"
            dao.observeMeasurements(dateParam, MeasurementType.WEIGHT).test {
                expectNoEvents()
            }
        }

    @Test
    fun observeMeasurements_correctDateAndMeasurementNonExists_shouldReturnEmptyFlow() =
        runTest {
            val dateParam = "2022-05"
            dao.observeMeasurements(dateParam, MeasurementType.WEIGHT).test {
                expectNoEvents()
            }
        }

    @Test
    fun observeMeasurements_inCorrectDateAndMeasurementNonExists_shouldReturnEmptyFlow() =
        runTest {
            val dateParam = "2022-05"
            dao.observeMeasurements(dateParam, MeasurementType.WEIGHT).test {
                expectNoEvents()
            }
        }

    @Test
    fun getMeasurements_correctDateAndMeasurementExists_shouldReturnMeasurementList() =
        runTest {
            val dateParam = "2022-05"
            measurements.forEach { dao.insertMeasurement(it) }
            val result = dao.getMeasurements(dateParam, MeasurementType.WEIGHT).size
            // -1 because there is two measurements with same id, so it is replaced
            val expectedResult = measurements.filter {
                it.measurementType == MeasurementType.WEIGHT && it.date.contains(
                    dateParam
                )
            }.size - 1

            assertEquals(expectedResult, result)
        }

    @Test
    fun getMeasurements_inCorrectDateAndMeasurementExists_shouldReturnMeasurementList() =
        runTest {
            val dateParam = "2022-04"
            measurements.forEach { dao.insertMeasurement(it) }
            val result = dao.getMeasurements(dateParam, MeasurementType.WEIGHT)
            val expectedResult = emptyList<MeasurementEntity>()

            assertEquals(expectedResult, result)
        }

    @Test
    fun getMeasurements_correctDateAndMeasurementNonExists_shouldReturnMeasurementList() =
        runTest {
            val dateParam = "2022-05"
            val result = dao.getMeasurements(dateParam, MeasurementType.WEIGHT)
            val expectedResult = emptyList<MeasurementEntity>()

            assertEquals(expectedResult, result)
        }

    @Test
    fun getMeasurements_inCorrectDateAndMeasurementNonExists_shouldReturnMeasurementList() =
        runTest {
            val dateParam = "2022-04"
            val result = dao.getMeasurements(dateParam, MeasurementType.WEIGHT)
            val expectedResult = emptyList<MeasurementEntity>()

            assertEquals(expectedResult, result)
        }
}

private object Constant {
    val measurements = listOf(
        MeasurementEntity(
            1,
            1.0,
            "2022-05-23",
            MeasurementType.WEIGHT
        ),
        MeasurementEntity(
            2,
            1.0,
            "2022-05-24",
            MeasurementType.BASAL_METABOLISM
        ),
        MeasurementEntity(
            3,
            1.0,
            "2022-05-26",
            MeasurementType.BMI
        ),
        MeasurementEntity(
            1,
            4.0,
            "2022-05-27",
            MeasurementType.WEIGHT
        ),
        MeasurementEntity(
            4,
            5.0,
            "2022-05-27",
            MeasurementType.WEIGHT
        ),
        MeasurementEntity(
            5,
            6.0,
            "2022-05-27",
            MeasurementType.WEIGHT
        )
    )
}
