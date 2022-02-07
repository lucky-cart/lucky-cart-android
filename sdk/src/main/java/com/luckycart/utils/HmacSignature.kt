package com.luckycart.utils

import java.lang.StringBuilder
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class HmacSignature {
    fun generateSignature(payload: String): String {
        val algorithm = "HmacSHA256"
        val key = "p#91J#i&00!QkdSPjgGNJq"

        return Mac.getInstance(algorithm)
            .apply { init(SecretKeySpec(key.toByteArray(), algorithm)) }
            .run { doFinal(payload.toByteArray()) }
            .let { byteArrayToHex(it) }
    }

    private fun byteArrayToHex(a: ByteArray): String {
        val sb = StringBuilder(a.size * 2)
        for (b in a) sb.append(String.format("%02x", b))
        return sb.toString()
    }


}