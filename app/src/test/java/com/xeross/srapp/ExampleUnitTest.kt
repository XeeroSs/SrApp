package com.xeross.srapp

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun test() {
        val enumPrevious = EnumTest::class.java.getPreviousOrNull(EnumTest.THREE)
        assertEquals(enumPrevious, EnumTest.TWO)
        
        val enumNext = EnumTest::class.java.getNextOrNull(EnumTest.TWO)
        assertEquals(enumNext, EnumTest.THREE)
    }
    
    private fun <T : Enum<T>> Class<T>.getNextOrNull(element: T): T? {
        val elements: Array<T> = this.enumConstants
        var index: Int = elements.indexOf(element)
        index = (index + 1).takeUnless { it >= elements.size } ?: return null
        return elements[index]
    }
    
    private fun <T : Enum<T>> Class<T>.getPreviousOrNull(element: T): T? {
        val elements: Array<T> = this.enumConstants
        var index: Int = elements.indexOf(element)
        index = (index - 1).takeUnless { it < 0 } ?: return null
        return elements[index]
    }
    
    @Suppress("unused")
    private enum class EnumTest {
        ONE, TWO, THREE
    }
    
}

