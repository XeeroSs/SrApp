package com.xeross.srapp

import com.xeross.srapp.utils.extensions.TimeExtensions.getAverageToMilliseconds
import com.xeross.srapp.utils.extensions.TimeExtensions.getBestToMilliseconds
import com.xeross.srapp.utils.extensions.TimeExtensions.getWorstToMilliseconds
import junitparams.JUnitParamsRunner
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
    
    
    private fun dates(): ArrayList<Long> {
        return arrayListOf(
            60000, /* 1m */
            3600000, /* 1h */
        )
    }
}