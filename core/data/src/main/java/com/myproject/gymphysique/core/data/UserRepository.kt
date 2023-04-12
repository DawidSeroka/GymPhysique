package com.myproject.gymphysique.core.data

import com.myproject.gymphysique.core.datastore.UserPreferencesDataSource
import com.myproject.gymphysique.core.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userPreferencesDataSource: UserPreferencesDataSource
) {
    fun observeUser(): Flow<UserData> =
        userPreferencesDataSource.userData

    suspend fun getUser(): UserData = userPreferencesDataSource.getUser()

    suspend fun saveUser(userData: UserData){
        userPreferencesDataSource.setUser(userData)
    }

    suspend fun setUserFirstName(firstName: String) {
        userPreferencesDataSource.setFirstName(firstName)
    }
    suspend fun setUserSurname(surname: String) {
        userPreferencesDataSource.setSurname(surname)
    }
    suspend fun setUserAge(age: Int) {
        userPreferencesDataSource.setAge(age)
    }
    suspend fun setUserHeight(height: Int) {
        userPreferencesDataSource.setHeight(height)
    }
    suspend fun setUserSex(isMale: Boolean) {
        userPreferencesDataSource.setSex(isMale)
    }
}