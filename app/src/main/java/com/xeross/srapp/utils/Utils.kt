package com.xeross.srapp.utils

object Utils {
    
    fun toPercent(total: Int, decreasedValue: Int): Int {
        if (decreasedValue <= 0) return 0
        if (total <= decreasedValue) return 100
        return decreasedValue * 100 / total
    }
    
}