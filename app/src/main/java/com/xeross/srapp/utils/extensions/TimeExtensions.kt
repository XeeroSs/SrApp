package com.xeross.srapp.utils.extensions

import com.xeross.srapp.utils.extensions.types.TimeType
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


object TimeExtensions {
    
    // 3599999 = 59m 59s 999ms
    private const val HOUR_TO_MILLISECONDS = 3600000
    private val simpleDateFormats = HashMap<TimeType, SimpleDateFormat>()
    
    init {
        TimeType.values().forEach {
            val simpleDateFormat = SimpleDateFormat(it.format, Locale.ENGLISH)
            simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
            simpleDateFormats[it] = simpleDateFormat
        }
    }
    
    fun String.convertTimeToSeconds(format: TimeType): Long {
        val simpleDateFormat = SimpleDateFormat(format.format, Locale.FRANCE)
        simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val calendar = Calendar.getInstance()
        val date = simpleDateFormat.parse(this) ?: return 0L
        calendar.time = date
        return (calendar.timeInMillis / 1000L)
    }
    
    fun Long.toFormatTime(): String {
        val format = when {
            ((this % 1000) == 0L) -> {
                if (this < HOUR_TO_MILLISECONDS) simpleDateFormats[TimeType.MINUTE_SECOND] else simpleDateFormats[TimeType.HOUR_MINUTE_SECOND]
            }
            this < HOUR_TO_MILLISECONDS -> simpleDateFormats[TimeType.MINUTE_SECOND_MILLISECONDS]
            else -> simpleDateFormats[TimeType.HOUR_MINUTE_SECOND_MILLISECONDS]
        }
        return format?.format(Date(this)) ?: "???"
    }
    
    /**
     * [this] format = "HH:mm:ss"
     */
    fun String.convertTimeToSeconds(): Long {
        val calendar = Calendar.getInstance()
        val date = simpleDateFormats[TimeType.HOUR_MINUTE_SECOND_MILLISECONDS]?.parse(this) ?: return 0L
        calendar.time = date
        return (calendar.timeInMillis / 1000L)
    }
    
    fun String.convertTimeToSeconds(format: SimpleDateFormat) {
    }
    
    fun ArrayList<Long>.getWorstToMilliseconds(): Long {
        if (this.isEmpty()) return 0L
        var totalSeconds = Long.MIN_VALUE
        for (time in this) {
            if (time > totalSeconds) totalSeconds = time
        }
        return (totalSeconds)
    }
    
    fun ArrayList<Long>.getBestToMilliseconds(): Long {
        if (this.isEmpty()) return 0L
        var totalSeconds = Long.MAX_VALUE
        for (time in this) {
            if (time < totalSeconds) totalSeconds = time
        }
        return (totalSeconds)
    }
    
    fun ArrayList<Long>.getAverageToMilliseconds(): Long {
        if (this.isEmpty()) return 0L
        var totalSeconds = 0L
        for (time in this) {
            totalSeconds += time
        }
        return if (totalSeconds <= 0) 0L else ((totalSeconds / (size)))
    }
    
}