package com.myproject.gymphysqiue.core.domain

import com.myproject.gymphysique.core.common.Result
import com.myproject.gymphysique.core.data.UserRepository
import com.myproject.gymphysique.core.decoder.ResponseData
import com.myproject.gymphysique.core.decoder.responseDecode.BodyCompositionResponseDecode
import kotlinx.coroutines.flow.last
import timber.log.Timber
import javax.inject.Inject

class DecodeDataUseCase @Inject constructor(
    private val bodyCompositionResponseDecode: BodyCompositionResponseDecode,
    private val userRepository: UserRepository
): suspend (ByteArray) -> Result<ResponseData> {
    override suspend fun invoke(byteArray: ByteArray): Result<ResponseData> {
        val user = userRepository.getUser()
        Timber.d("user = $user")
        val sex = if(user.isMale) " male" else "female"
        return bodyCompositionResponseDecode.decodeBodyComposition(byteArray,sex,user.height,user.age)
    }
}