package com.xeross.srapp

import com.xeross.srapp.utils.extensions.TimeExtensions.getAverageToMilliseconds
import com.xeross.srapp.utils.extensions.TimeExtensions.getBestToMilliseconds
import com.xeross.srapp.utils.extensions.TimeExtensions.getWorstToMilliseconds
import com.xeross.srapp.utils.extensions.TimeExtensions.toFormatTime
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import junitparams.naming.TestCaseName
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.*

@RunWith(JUnitParamsRunner::class)
class TimeTest {
    
    private val simpleDate = SimpleDateFormat("HH:mm:ss")
    
    init {
        simpleDate.timeZone = TimeZone.getTimeZone("UTC")
    }
    
    @Test
    @TestCaseName("[{index}] | '{params}'")
    fun `Format time`() {
        timesToFormat().forEach {
            println(it.toFormatTime())
            println("")
            println("-------------------")
            println("")
        }
    }
    
    private fun timesToFormat(): ArrayList<Long> {
        return arrayListOf(
            2038929, /* idk */
            1925913, /* idk */
            1953674, /* idk */
            2000849, /* idk */
            3596400000, /* idk */
        )
    }
    
    @Test
    @TestCaseName("[{index}] | '{params}'")
    fun `Average time`() {
        val average = simpleDate.format(dates().getAverageToMilliseconds())
        println(dates().getAverageToMilliseconds())
        Assert.assertEquals(average, "00:30:30")
    }
    
    @Test
    @TestCaseName("[{index}] | '{params}'")
    fun `Worst time`() {
        val worst = simpleDate.format(dates().getWorstToMilliseconds())
        Assert.assertEquals(worst, "01:00:00")
    }
    
    @Test
    @TestCaseName("[{index}] | '{params}'")
    fun `Best time`() {
        val best = simpleDate.format(dates().getBestToMilliseconds())
        Assert.assertEquals(best, "00:01:00")
    }
    
    @Test
    @Parameters(method = "times")
    @TestCaseName("[{index}] | '{params}'")
    fun `to format time`(time: Long) {
        println(time.toFormatTime())
        
    }
    
    @Test
    @Parameters(method = "timesWithoutHours")
    @TestCaseName("[{index}] | '{params}'")
    fun `to format time without hour`(time: Long) {
        println(time.toFormatTime())
    }
    
    @Test
    @Parameters(method = "timesWithoutMilliseconds")
    @TestCaseName("[{index}] | '{params}'")
    fun `to format time without milliseconds`(time: Long) {
        println(time.toFormatTime())
        
    }
    
    @Test
    @Parameters(method = "timesWithoutHoursAndMilliseconds")
    @TestCaseName("[{index}] | '{params}'")
    fun `to format time without hours and milliseconds`(time: Long) {
        println(time.toFormatTime())
    }
    
    private fun times(): ArrayList<Long> {
        return arrayListOf(
            21600356, /* idk */
            21600016, /* idk */
        )
    }
    
    private fun timesWithoutHours(): ArrayList<Long> {
        return arrayListOf(
            60156, /* idk */
            60023, /* idk */
        )
    }
    
    private fun timesWithoutMilliseconds(): ArrayList<Long> {
        return arrayListOf(
            21600000, /* 6h */
            31600000, /* idk */
        )
    }
    
    private fun timesWithoutHoursAndMilliseconds(): ArrayList<Long> {
        return arrayListOf(
            60000, /* 1m */
            60000 * 2, /* 2m */
            1000 * 3, /*3s*/
        )
    }
    
    
    private fun dates(): ArrayList<Long> {
        return arrayListOf(
            60000, /* 1m */
            3600000, /* 1h */
        )
    }
}