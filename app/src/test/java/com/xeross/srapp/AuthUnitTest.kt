package com.xeross.srapp

import com.xeross.srapp.utils.extensions.UtilsExtensions.isFormatEmail
import com.xeross.srapp.utils.extensions.UtilsExtensions.isValidPassword
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import junitparams.naming.TestCaseName
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
class AuthUnitTest {
    
    @Test
    @Parameters(method = "validPassword")
    @TestCaseName("[{index}] | '{params}'")
    fun `Valid passwords`(password: String) {
        Assert.assertTrue(password.isValidPassword())
    }
    
    @Test
    @Parameters(method = "invalidPassword")
    @TestCaseName("[{index}] | '{params}'")
    fun `Invalid passwords`(password: String) {
        Assert.assertFalse(password.isValidPassword())
    }
    
    @Test
    @Parameters(method = "validEmail")
    @TestCaseName("[{index}] | '{params}'")
    fun `Valid emails`(email: String) {
        Assert.assertTrue(email.isFormatEmail())
    }
    
    @Test
    @Parameters(method = "invalidEmail")
    @TestCaseName("[{index}] | '{params}'")
    fun `Invalid emails`(email: String) {
        Assert.assertFalse(email.isFormatEmail())
    }
    
    
    fun validPassword(): Array<String> {
        return arrayOf(
            "test1_test",
            "tEst1_test",
            "TEST_41TEST",
        )
    }
    
    fun validEmail(): Array<String> {
        return arrayOf(
            "test@domain.com",
            "tes.-_*$^!t@domain.com",
            "test.test@domain.com",
            "t@domain.com",
            "t.e.s.t@domain.com",
            "te.s.t@do.mai.ne.com",
        )
    }
    
    fun invalidEmail(): Array<String> {
        return arrayOf(
            "testdomain.com",
            "te@st@domain.com",
            "test@domain..com",
            "te st@domain.com"
            //    "test@gma_il.com",
        )
    }
    
    fun invalidPassword(): Array<String> {
        return arrayOf(
            "tesT1_f", // < 8 characters
            "test_test", // No digit
            "1234567_", // No letters
            " ", // Blank
            "", // Empty
        )
    }
}