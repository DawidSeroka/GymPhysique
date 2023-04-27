package com.myproject.gymphysqiue.core.domain

import com.myproject.gymphysique.core.data.UserRepository
import com.myproject.gymphysique.core.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) : () -> Flow<UserData> {
    override fun invoke(): Flow<UserData> {
        return userRepository.observeUser()
    }
}
