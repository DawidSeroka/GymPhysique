package com.myproject.gymphysique.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import com.myproject.gymphysique.core.common.Gender
import com.myproject.gymphysique.core.model.UserData
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {
    val userData = userPreferences.data
        .map {
            UserData(
                firstName = it.firstName,
                surname = it.surname,
                age = it.age,
                height = it.height,
                gender = it.gender
            )
        }

    suspend fun getUser(): UserData =
        userData.firstOrNull() ?: UserData("", "", 0, 0, "")


    suspend fun setUser(userData: UserData) {
        try {
            userPreferences.updateData {
                it.copy {
                    firstName = userData.firstName
                    surname = userData.surname
                    age = userData.age
                    height = userData.height
                    gender = userData.gender
                }
            }
        } catch (ioException: IOException) {
            Log.e("GymPhysique", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setFirstName(firstNameParam: String) {
        try {
            userPreferences.updateData {
                it.copy {
                    firstName = firstNameParam
                }
            }
        } catch (ioException: IOException) {
            Log.e("GymPhysique", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setSurname(surnameParam: String) {
        try {
            userPreferences.updateData {
                it.copy {
                    surname = surnameParam
                }
            }
        } catch (ioException: IOException) {
            Log.e("GymPhysique", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setAge(ageParam: Int) {
        try {
            userPreferences.updateData {
                it.copy {
                    age = ageParam
                }
            }
        } catch (ioException: IOException) {
            Log.e("GymPhysique", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setHeight(heightParam: Int) {
        try {
            userPreferences.updateData {
                it.copy {
                    height = heightParam
                }
            }
        } catch (ioException: IOException) {
            Log.e("GymPhysique", "Failed to update user preferences", ioException)
        }
    }

    suspend fun setGender(genderParam: String) {
        try {
            userPreferences.updateData {
                it.copy {
                    gender = genderParam
                }
            }
        } catch (ioException: IOException) {
            Log.e("GymPhysique", "Failed to update user preferences", ioException)
        }
    }
}