package com.myproject.gymphysique.core.decoder.responseDecode

import com.myproject.gymphysique.core.decoder.ResponseData
import com.myproject.gymphysique.core.common.Result

sealed interface ResponseDecode{
    fun decodeBodyComposition(byteArray: ByteArray): Result<ResponseData>
}