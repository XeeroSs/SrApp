package com.xeross.srapp.utils.extensions

import java.util.regex.Pattern


object UtilsExtensions {
    
    private const val HEX_REG = "#%06X"
    private const val HEX_BASE = 0xFFFFFF
    
    // TODO("test regex with unit test")
    private const val EMAIL_FORMAT = "^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$"
    
    //   private const val PASSWORD_FORMAT = "^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*\\s).{8,}\$"
    private const val PASSWORD_FORMAT = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;'_,?/*~\$^+=<>]).{8,}\$"
    // private const val EMAIL_FORMAT = "/^[^\\W][a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)*\\@[a-zA-Z0-9_]+(\\.[a-zA-Z0-9_]+)*\\.[a-zA-Z]{2,4}\$/"
    
    
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
    
    fun String.isFormatEmail(): Boolean {
        val pattern = Pattern.compile(EMAIL_FORMAT, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(this)
        return matcher.matches()
    }
    
    fun String.isValidPassword(): Boolean {
        val pattern = Pattern.compile(PASSWORD_FORMAT, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(this)
        return matcher.matches()
    }
    
}