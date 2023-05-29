package com.myproject.gymphysique.core.data

import com.myproject.gymphysique.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun observeUser(): Flow<UserData>

    suspend fun getUser(): UserData

    suspend fun saveUser(userData: UserData): Result<UserData>

    suspend fun setUserFirstName(firstName: String)

    suspend fun setUserSurname(surname: String)

    suspend fun setUserAge(age: Int)

    suspend fun setUserHeight(height: Int)

    suspend fun setUserGender(gender: String)
}
