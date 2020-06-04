package com.app.core.util

import java.math.BigInteger
import java.nio.charset.Charset
import java.security.MessageDigest

/**
 * @param input  find string
 * @param start  startIndex
 * @return int array 2 : 1.start 2.end
 */
fun <T : String?> T.range(input: String?, start: Int = 0): IntArray {
    val range = IntArray(2)
    range[0] = -1
    range[1] = -1
    if (!isNullOrBlank() && this!!.length > start) {
        if (!input.isNullOrBlank()) {
            range[0] = this.indexOf(input, start)
            if (range[0] != -1) range[1] = range[0] + input.length
        }
    }
    return range
}

/**
 * @param input  find string
 * @return int array 2 : 1.start 2.end
 */
fun <T : String?> T.rangeLast(input: String?, start: Int = 0): IntArray {
    val range = IntArray(2)
    range[0] = -1
    range[1] = -1
    if (!isNullOrBlank() && this!!.length > start) {
        if (!input.isNullOrBlank()) {
            range[0] = this.lastIndexOf(input)
            if (range[0] != -1) range[1] = range[0] + input.length
        }
    }
    return range
}

fun <T: String?> T.sha256(): String {
    val messageDigest: MessageDigest = MessageDigest.getInstance("SHA-256")
    messageDigest.update(this?.toByteArray(Charset.forName("UTF-8")))
    val byteDigest = messageDigest.digest()
    val hex = String.format("%064x", BigInteger(1, byteDigest))
    return hex
}