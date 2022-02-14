package com.xeross.srapp.extensions

import java.lang.String

object UtilsExtensions {
    
    const val HEX_REG = "#%06X"
    const val HEX_BASE = 0xFFFFFF
    
    fun Int.toHexColorString() = String.format(HEX_REG, HEX_BASE and this)
    
}