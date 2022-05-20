package com.xeross.srapp

import com.xeross.srapp.utils.Utils.toPercent
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import junitparams.naming.TestCaseName
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(JUnitParamsRunner::class)
class UtilTest {
    
    @Test
    @Parameters(method = "digitToHigherThanFifty")
    @TestCaseName("[{index}] | '{params}'")
    fun `Higher than 50`(value: Int) {
        val result = toPercent(100, value)
        println(result)
        Assert.assertTrue(result in 50..100)
    }
    
    @Test
    @Parameters(method = "digitToLowerThanFifty")
    @TestCaseName("[{index}] | '{params}'")
    fun `Lower than 50`(value: Int) {
        val result = toPercent(100,value)
        println(result)
        Assert.assertTrue(result in 0..50)
    }
    
    private fun digitToHigherThanFifty(): ArrayList<Int> {
        // total = 100
        return arrayListOf(
            100, /* 100 of 100 = 100% */
            50, /* 50 of 100 = 50% */
            75, /* 75 of 100 = 75% */
            101, /*  101 of 100 (Incorrect operation, the total value is lower at the decreased value, so return '100')) = 100% */
        )
    }
    
    private fun digitToLowerThanFifty(): ArrayList<Int> {
        // total = 100
        return arrayListOf(
            0, /* 0 of 100 = 0% */
            50, /* 50 of 100 = 50% */
            25, /* 75 of 100 = 75% */
            5, /* 5 of 100 = 5% */
            -9999, /*  -9999 of 100 (Incorrect operation, the decreased value is lower at 0, so return '0')) = 0% */
        )
    }
}