package com.myproject.gymphysqiue.core.domain.settings

import com.myproject.gymphysique.core.data.UserRepository
import com.myproject.gymphysique.core.model.UserData
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
) : suspend () -> UserData {
    override suspend fun invoke(): UserData {
        return repository.getUser()
    }
}
