package com.xeross.srapp.components.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.xeross.srapp.R
import com.xeross.srapp.components.ui.models.DataBar
import kotlin.math.abs


/**
 * @author XeroSs
 * */
// TODO("to do")
class GraphicBar(context: Context, attrs: AttributeSet) : View(context, attrs) {
    
    private var bars: ArrayList<DataBar> = ArrayList()
    private var barAmount = 0L
    private val widthBar = (15f)
    private val maxHeightBar = 60f
    private val spaceBetweenBar = (widthBar / 2) + ((widthBar / 2) / 2)
    private val space = 21f
    private lateinit var paint: Paint
    private val color: Int
    
    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.graphic_bar,
            0, 0).apply {
            
            color = try {
                this.getColor(R.styleable.graphic_bar_bar_color, 222222)
            } catch (e: Exception) {
                e.printStackTrace()
                context.getColor(R.color.pink_gradient)
            } finally {
                this.recycle()
            }
        }
        
        paint = Paint().apply {
            isAntiAlias = true
            color = context.getColor(R.color.pink_gradient_variant_2)
            style = Paint.Style.FILL
        }
    }
    
    override fun onDraw(canvas: Canvas?) {
        
        canvas?.let {
            
            barAmount = 0
            
            val availableWidth = abs(measuredWidth - (space * 2)).toInt()
            
            val totalBars = (availableWidth / (widthBar + spaceBetweenBar)).toInt()
            val widthWithoutOverflow = (totalBars * (widthBar + spaceBetweenBar))
            val overflow = (availableWidth - widthWithoutOverflow)
            
            var startBar = (space + ((overflow / 2) + (spaceBetweenBar / 2)))
            
            if (!bars.isNullOrEmpty()) {
                val barHighest = bars.reduce { acc, dataBar ->
                    if (acc.data > dataBar.data) acc else dataBar
                }.data
                val barLowest = bars.reduce { acc, dataBar ->
                    if (acc.data < dataBar.data) acc else dataBar
                }.data
                
                var indexBars = 0
                
                repeat(totalBars) {
                    
                    bars.getOrNull(indexBars)?.let { bar ->
                        val data = bar.data
                        val percent = (((data - barLowest.toDouble()) /
                                (barHighest.toDouble() - barLowest)) * 100)
                        // TODO("if(percent == 0.0)")
                        
                        val bottomBar = (if (measuredHeight <= space) space
                        else (measuredHeight - space).coerceAtLeast(space))
                        
                        val topHeight = measuredHeight - (space * 5)
                        val overflowHeight = (((/*bottomBar + */topHeight) * percent.toInt()) / 100)
                        
                        val heightBar = (bottomBar - maxHeightBar) - overflowHeight
                        
                        
                        drawBar(canvas, startBar, bottomBar, heightBar)
                        
                        
                    } ?: drawBar(canvas, startBar)
                    
                    barAmount++
                    indexBars++
                    startBar += (widthBar + spaceBetweenBar)
                }
                return
            }
            
            repeat(totalBars) {
                drawBar(canvas, startBar)
                barAmount++
                startBar += (widthBar + spaceBetweenBar)
            }
            
        }
        
    }
    
    fun setBars(bars: ArrayList<DataBar>) {
        this.bars = bars
        requestLayout()
        invalidate()
    }
    
    fun getBarTotal() = barAmount
    
    private fun drawBar(canvas: Canvas, start: Float) {
        
        val bottomBar = if (measuredHeight <= space) space
        else (measuredHeight - space).coerceAtLeast(space)
        
        val heightBar = (bottomBar - maxHeightBar)
        
        drawBar(canvas, start, heightBar, bottomBar)
    }
    
    private fun drawBar(canvas: Canvas, startBar: Float, top: Float, bottom: Float) {
        canvas.save()
        val rect = RectF(startBar, top, (startBar + widthBar), bottom)
        canvas.drawRoundRect(rect, 15f, 15f, paint)
        canvas.restore()
    }
    
}