package com.xeross.srapp.extensions

import java.lang.String


object UtilsExtensions {
    
    private const val HEX_REG = "#%06X"
    private const val HEX_BASE = 0xFFFFFF
    
    fun Int.toHexColorString(): kotlin.String = String.format(HEX_REG, HEX_BASE and this)
    
    /**
     * Get the next element from [Enum] based on Enum::values order
     *
     * @param [element] element [Enum] to get the next element
     * @return [T] next element from [Enum]
     */
    fun <T : Enum<T>> Class<T>.getNextOrNull(element: T): T? {
        val elements: Array<T> = this.enumConstants
        var index: Int = elements.indexOf(element)
        index = (index + 1).takeUnless { it >= elements.size } ?: return null
        return elements[index]
    }
    
    /**
     * Get the previous element from [Enum] based on Enum::values order
     *
     * @param [element] element [Enum] to get the previous element
     * @return [T] previous element from [Enum]
     */
    fun <T : Enum<T>> Class<T>.getPreviousOrNull(element: T): T? {
        val elements: Array<T> = this.enumConstants
        var index: Int = elements.indexOf(element)
        index = (index - 1).takeUnless { it < 0 } ?: return null
        return elements[index]
    }
    
}