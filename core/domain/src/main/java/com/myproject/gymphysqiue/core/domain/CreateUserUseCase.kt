package com.myproject.gymphysqiue.core.domain

import com.myproject.gymphysique.core.data.UserRepository
import com.myproject.gymphysique.core.model.UserData
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
): suspend (String,String,String,Int,Int) -> Unit{
    override suspend fun invoke(firstName: String, surname: String, gender: String, height: Int, age: Int) {
        userRepository.saveUser(UserData(firstName,surname,age,height,gender))
    }
}