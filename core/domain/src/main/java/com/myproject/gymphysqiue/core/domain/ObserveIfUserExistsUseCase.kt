package com.myproject.gymphysqiue.core.domain

import com.myproject.gymphysique.core.data.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveIfUserExistsUseCase @Inject constructor(
    private val repository: UserRepository
) : () -> Flow<Boolean> {
    override fun invoke(): Flow<Boolean> {
        return repository.observeUser().map { it.firstName.isNotEmpty() }
    }
}
