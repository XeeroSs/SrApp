package com.xeross.srapp

import com.xeross.srapp.utils.extensions.TimeExtensions.getAverageToSeconds
import com.xeross.srapp.utils.extensions.TimeExtensions.getBestToSeconds
import com.xeross.srapp.utils.extensions.TimeExtensions.getWorstToSeconds
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
        val average = simpleDate.format(dates().getAverageToSeconds())
        Assert.assertEquals(average, "00:01:27")
    }
    
    @Test
    @TestCaseName("[{index}] | '{params}'")
    fun `Worst time`() {
        val worst = simpleDate.format(dates().getWorstToSeconds())
        Assert.assertEquals(worst, "00:00:53")
    }
    
    @Test
    @TestCaseName("[{index}] | '{params}'")
    fun `Best time`() {
        val best = simpleDate.format(dates().getBestToSeconds())
        Assert.assertEquals(best, "00:01:52")
    }
    
    
    private fun dates(): ArrayList<Long> {
        return arrayListOf(
            96, /* 96s */
            53, /* 53s */
            112, /* 112s */
        )
    }
}