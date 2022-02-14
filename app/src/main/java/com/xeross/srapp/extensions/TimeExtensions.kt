package com.xeross.srapp.extensions

import com.xeross.srapp.extensions.types.TimeType
import java.text.SimpleDateFormat
import java.util.*


object TimeExtensions {
    
    private val simpleDateFormat = SimpleDateFormat("mm:ss", Locale.ENGLISH)
    
    init {
        simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
    }
    
    fun String.convertTimeToSeconds(format: TimeType): Long {
        val simpleDateFormat = SimpleDateFormat(format.format, Locale.FRANCE)
        simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val calendar = Calendar.getInstance()
        val date = simpleDateFormat.parse(this) ?: return 0L
        calendar.time = date
        return (calendar.timeInMillis / 1000L)
    }
    
    /**
     * [this] format = "HH:mm:ss"
     */
    fun String.convertTimeToSeconds(): Long {
        val calendar = Calendar.getInstance()
        val date = simpleDateFormat.parse(this) ?: return 0L
        calendar.time = date
        return (calendar.timeInMillis / 1000L)
    }
    
    fun String.convertTimeToSeconds(format: SimpleDateFormat) {
    
    }
    
}