package com.luckycart.utils

import java.lang.StringBuilder
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class HmacSignature {
    fun generateSignature(payload: String): String {

        return Mac.getInstance(HMAC_ALGORITHM)
            .apply { init(SecretKeySpec(AUTH_SECRET.toByteArray(), algorithm)) }
            .run { doFinal(payload.toByteArray()) }
            .let { byteArrayToHex(it) }
    }

    private fun byteArrayToHex(a: ByteArray): String {
        val sb = StringBuilder(a.size * 2)
        for (b in a) sb.append(String.format("%02x", b))
        return sb.toString()
    }


}