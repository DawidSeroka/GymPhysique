package com.myproject.gymphysqiue.core.domain.settings

import com.myproject.gymphysique.core.common.Gender
import com.myproject.gymphysique.core.data.UserRepository
import com.myproject.gymphysique.core.model.UserData
import javax.inject.Inject

class SaveUserDataUseCase @Inject constructor(
    private val repository: UserRepository
) : suspend (String, String, Int, Int, Gender) -> Result<UserData> {
    override suspend fun invoke(
        firstName: String,
        surname: String,
        height: Int,
        age: Int,
        gender: Gender
    ): Result<UserData> {
        return repository.saveUser(UserData(firstName, surname, age, height, gender.name))
    }
}