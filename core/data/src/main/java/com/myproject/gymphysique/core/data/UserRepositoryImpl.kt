package com.myproject.gymphysique.core.data

import com.myproject.gymphysique.core.datastore.UserPreferencesDataSource
import com.myproject.gymphysique.core.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userPreferencesDataSource: UserPreferencesDataSource
) : UserRepository {
    override fun observeUser(): Flow<UserData> =
        userPreferencesDataSource.userData

    override suspend fun getUser(): UserData = userPreferencesDataSource.getUser()

    override suspend fun saveUser(userData: UserData): Result<UserData> {
        return userPreferencesDataSource.setUser(userData)
    }

    override suspend fun setUserFirstName(firstName: String) {
        userPreferencesDataSource.setFirstName(firstName)
    }

    override suspend fun setUserSurname(surname: String) {
        userPreferencesDataSource.setSurname(surname)
    }

    override suspend fun setUserAge(age: Int) {
        userPreferencesDataSource.setAge(age)
    }

    override suspend fun setUserHeight(height: Int) {
        userPreferencesDataSource.setHeight(height)
    }

    override suspend fun setUserGender(gender: String) {
        userPreferencesDataSource.setGender(gender)
    }
}
