package com.myproject.gymphysqiue.core.domain

import com.myproject.gymphysique.core.data.UserRepository
import com.myproject.gymphysique.core.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class ObserveIfUserExistsUseCase @Inject constructor(
    private val userRepository: UserRepository
): () -> Flow<UserData>{
    override fun invoke(): Flow<UserData> {
        return userRepository.observeUser()
    }
}