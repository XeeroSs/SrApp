package com.xeross.srapp.utils.extensions

import android.content.Context
import com.xeross.srapp.R
import java.util.*


object TimeExtensions {
    
    // 3599999 = 59m 59s 999ms
    private const val HOUR_TO_MILLISECONDS = 3600000
    
    private const val ONE_SECOND: Long = 1000
    private const val ONE_MINUTE = ONE_SECOND * 60
    private const val ONE_HOUR = ONE_MINUTE * 60
    private const val ONE_DAY = ONE_HOUR * 24
    private const val ONE_YEARS = ONE_DAY * 365
    
    fun Long.toFormatTime(): String {
        
        val hours = this / (1000L * 60L * 60L)
        
        val builder = StringBuilder()
        
        if (hours > 0) {
            if (hours < 10) builder.append("0")
            builder.append(hours).append(":")
        }
        
        val minutes = (this / (1000L * 60L)) % 60L
        
        if (minutes > 0) {
            if (minutes < 10) builder.append("0")
            builder.append(minutes).append(":")
        } else builder.append("00:")
        
        val seconds = (this / (1000L)) % 60
        
        if (seconds > 0) {
            if (seconds < 10) builder.append("0")
            builder.append(seconds)
        } else builder.append("00")
        
        val milliseconds = this % 1000L
        
        if (milliseconds > 0) {
            builder.append(".")
            if (milliseconds < 100) builder.append("0")
            if (milliseconds < 10) builder.append("0")
            builder.append(milliseconds).append("")
        }
        
        return builder.toString()
    }
    
    fun ArrayList<Long>.getWorstToMilliseconds(): Long {
        if (this.isEmpty()) return 0L
        var totalSeconds = Long.MIN_VALUE
        for (time in this) {
            if (time > totalSeconds) totalSeconds = time
        }
        return (totalSeconds)
    }
    
    fun List<Long>.getWorstToMilliseconds(): Long {
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
    
    fun List<Long>.getBestToMilliseconds(): Long {
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
    
    fun List<Long>.getAverageToMilliseconds(): Long {
        if (this.isEmpty()) return 0L
        var totalSeconds = 0L
        for (time in this) {
            totalSeconds += time
        }
        return if (totalSeconds <= 0) 0L else ((totalSeconds / (size)))
    }
    
    fun Long.toMillisecondFromTimeInMilliseconds(): Long {
        return (this % 1000L).coerceAtLeast(0)
    }
    
    fun Long.toSecondFromTimeInMilliseconds(): Long {
        return ((this / (1000L * 60L)) % 60L).coerceAtLeast(0)
    }
    
    fun Long.toMinuteFromTimeInMilliseconds(): Long {
        return ((this / (1000L)) % 60).coerceAtLeast(0)
    }
    
    fun Long.toHourFromTimeInMilliseconds(): Long {
        return (this / (1000L * 60L * 60L)).coerceAtLeast(0)
    }
    
    fun Long.timeAgoToString(context: Context, date: Date): String {
        val duration = date.time - this
        var time: Long = duration / ONE_YEARS
        
        if (time > 0) {
            val yearOrYears = context.resources.getString(R.string.year) + if (time > 1) "s" else ""
            return context.resources.getString(R.string.time_ago, time, yearOrYears)
        }
        
        time = duration / ONE_DAY
        
        if (time > 0) {
            val dayOrDays = context.resources.getString(R.string.day) + if (time > 1) "s" else ""
            return context.resources.getString(R.string.time_ago, time, dayOrDays)
        }
        
        time = duration / ONE_HOUR
        
        if (time > 0) {
            val hourOrHours = context.resources.getString(R.string.hour) + if (time > 1) "s" else ""
            return context.resources.getString(R.string.time_ago, time, hourOrHours)
        }
        
        time = duration / ONE_MINUTE
        
        if (time > 0) {
            val minuteOrMinutes = context.resources.getString(R.string.minute) + if (time > 1) "s" else ""
            return context.resources.getString(R.string.time_ago, time, minuteOrMinutes)
        }
        
        time = duration / ONE_SECOND
        
        if (time > 0) {
            val secondOrSeconds = context.resources.getString(R.string.second) + if (time > 1) "s" else ""
            return context.resources.getString(R.string.time_ago, time, secondOrSeconds)
        }
        
        val second = context.resources.getString(R.string.second)
        return context.resources.getString(R.string.time_ago, 0, second)
    }
    
}