package com.myproject.gymphysique.core.testing.repository

import com.myproject.gymphysique.core.data.UserRepository
import com.myproject.gymphysique.core.model.UserData
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf

class FakeUserRepository : UserRepository {

    private val userFlow: MutableSharedFlow<UserData> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun observeUser(): Flow<UserData> {
        if (userFlow.replayCache.isEmpty()) {
            return flowOf(UserData("", "", 0, 0, "", ""))
        }
        return userFlow
    }

    override suspend fun getUser(): UserData {
        return userFlow.replayCache.firstOrNull() ?: UserData(
            firstName = "fakeFirstname",
            surname = "fakeSurname",
            age = 99,
            height = 180,
            gender = "MALE",
            uri = "testUri"
        )
    }

    override suspend fun saveUser(userData: UserData): Result<UserData> {
        return if (userFlow.tryEmit(userData)) {
            Result.success(userData)
        } else {
            Result.failure(Exception("testException"))
        }
    }

    override suspend fun setUserFirstName(firstName: String) {
        val userData = getUser().copy(firstName = firstName)
        userFlow.tryEmit(userData)
    }

    override suspend fun setUserSurname(surname: String) {
        val userData = getUser().copy(surname = surname)
        userFlow.tryEmit(userData)
    }

    override suspend fun setUserAge(age: Int) {
        val userData = getUser().copy(age = age)
        userFlow.tryEmit(userData)
    }

    override suspend fun setUserHeight(height: Int) {
        val userData = getUser().copy(height = height)
        userFlow.tryEmit(userData)
    }

    override suspend fun setUserGender(gender: String) {
        val userData = getUser().copy(gender = gender)
        userFlow.tryEmit(userData)
    }
}
