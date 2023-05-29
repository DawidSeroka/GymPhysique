package com.myproject.gymphysique.core.testing

import com.myproject.gymphysique.core.data.UserRepository
import com.myproject.gymphysique.core.model.UserData
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf

class FakeUserRepository : UserRepository {

    private val userDataFlow: MutableSharedFlow<UserData> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override fun observeUser(): Flow<UserData> {
        return if (userDataFlow.replayCache.isEmpty()) {
            flowOf(UserData("", "", 0, 0, "", ""))
        } else {
            userDataFlow
        }
    }

    override suspend fun getUser(): UserData {
        return userDataFlow.replayCache.first()
    }

    override suspend fun saveUser(userData: UserData): Result<UserData> {
        userDataFlow.tryEmit(userData)
        return Result.success(userData)
    }

    override suspend fun setUserFirstName(firstName: String) {
        val userData = getUser().copy(firstName = firstName)
        userDataFlow.tryEmit(userData)
    }

    override suspend fun setUserSurname(surname: String) {
        val userData = getUser().copy(surname = surname)
        userDataFlow.tryEmit(userData)
    }

    override suspend fun setUserAge(age: Int) {
        val userData = getUser().copy(age = age)
        userDataFlow.tryEmit(userData)
    }

    override suspend fun setUserHeight(height: Int) {
        val userData = getUser().copy(height = height)
        userDataFlow.tryEmit(userData)
    }

    override suspend fun setUserGender(gender: String) {
        val userData = getUser().copy(gender = gender)
        userDataFlow.tryEmit(userData)
    }
}
