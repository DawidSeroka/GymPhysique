package com.myproject.gymphysqiue.core.domain.decode

import com.myproject.gymphysique.core.data.UserRepository
import com.myproject.gymphysique.core.decoder.responseDecode.BodyCompositionResponseDecode
import com.myproject.gymphysique.core.model.Measurement
import timber.log.Timber
import javax.inject.Inject

class DecodeDataUseCase @Inject constructor(
    private val bodyCompositionResponseDecode: BodyCompositionResponseDecode,
    private val decodeResponseUseCase: DecodeResponseUseCase,
    private val userRepository: UserRepository
) : suspend (ByteArray) -> List<Measurement> {
    override suspend fun invoke(byteArray: ByteArray): List<Measurement>{
        val user = userRepository.getUser()
        val response = bodyCompositionResponseDecode.decodeBodyComposition(
            byteArray,
            user.gender,
            user.height,
            user.age
        )
        return decodeResponseUseCase(response)
    }
}