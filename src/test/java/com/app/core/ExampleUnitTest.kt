package com.app.core

import com.app.core.util.sha256
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
//        SHA256('Frt#sA86-Gf~hy'.'303vtb15si'.'M@l-MHNB4~3Ch7'.'be2de102d599475abfe604a8f0f88342'.'chl99^47!5#abfe@604aTL#2365-85-9-1')
        System.out.println("Frt#sA86-Gf~hy303vtb15siM@l-MHNB4~3Ch7be2de102d599475abfe604a8f0f88342chl99^47!5#abfe@604aTL#2365-85-9-1".sha256())
        assertEquals(4, (2 + 2).toLong())
    }
}