package com.myproject.gymphysique.core.decoder.responseDecode

import com.myproject.gymphysique.core.common.Result
import com.myproject.gymphysique.core.decoder.ResponseData

sealed interface ResponseDecode {
    fun decodeBodyComposition(byteArray: ByteArray): Result<ResponseData>
}
