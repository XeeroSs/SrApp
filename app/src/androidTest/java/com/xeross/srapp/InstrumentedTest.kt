package com.xeross.srapp

import android.content.Context
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.Timestamp
import com.xeross.srapp.utils.extensions.TimeExtensions.timeAgoToString
import org.junit.Before
import org.junit.Test
import java.util.*

class InstrumentedTest {
    
    private val TAG = "InstrumentedTest"
    
    private lateinit var context: Context
    
    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }
    
    @Test
    fun test() {
        times().forEach {
            Log.i(TAG, it.timeAgoToString(context, Date()))
        }
    }
    
    private fun times(): ArrayList<Long> {
        return arrayListOf(
            (Timestamp.now().seconds * 1000),
            (Timestamp.now().seconds * 1000) - (((1000) * 60)),
            (Timestamp.now().seconds * 1000) - (((1000) * 60) * 60),
            (Timestamp.now().seconds * 1000) - (((1000) * 60) * 60) * 24,
            (Timestamp.now().seconds * 1000) - ((((1000) * 60) * 60) * 24) * 56L,
            (Timestamp.now().seconds * 1000) - ((((1000) * 60) * 60) * 24) * 365L,
        )
    }
    
}