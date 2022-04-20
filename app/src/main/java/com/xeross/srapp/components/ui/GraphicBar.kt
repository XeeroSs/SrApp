package com.xeross.srapp.components.ui

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.xeross.srapp.components.ui.models.DataBar
import kotlin.math.abs

/**
 * @author XeroSs
 * */
// TODO("to do")
class GraphicBar(context: Context, private val attrs: AttributeSet) : View(context, attrs) {
    
    private val bars = ArrayList<DataBar>()
    private val widthBar = (15 * 3)
    private val cornerBar = (5 * 3)
    private val spaceBetweenBar = (5 * 3)
    
    companion object {
        private const val TAG = "GraphicBarLog"
    }
    
    private fun sendLog(message: Any) {
        Log.i(TAG, message.toString())
    }
    
    init {
        setUp()
    }
    
    private fun setUp() {
    }
    
    override fun onDraw(canvas: Canvas?) {
        val minimumHeight = (widthBar * 10)
        val totalBars = abs(measuredWidth / widthBar)
        
        println(widthBar - (totalBars * spaceBetweenBar))
        
        val margin = abs(measuredWidth - (totalBars * widthBar))
        
        val defaultHeight = measuredHeight
        
        println("totalBars = ${totalBars}")
        println("margin = ${margin}")
        
        var start = margin
        
        for (i in 0..totalBars) {
            start = (start + widthBar)
            
        }
        
    }
    
}