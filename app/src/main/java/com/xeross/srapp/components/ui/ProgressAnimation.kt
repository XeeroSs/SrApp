package com.xeross.srapp.components.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View

/**
 * Source: https://stackoverflow.com/questions/48520655/how-to-create-a-repeating-animated-moving-gradient-drawable-like-an-indetermina
 */
class ProgressAnimation @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    
    private val speedInPercentage = 1.5f
    private var xMovement: Float = 0.0f
    private val rightDrawable: GradientDrawable = GradientDrawable()
    private val leftDrawable: GradientDrawable = GradientDrawable()
    
    init {
        rightDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
        rightDrawable.orientation = GradientDrawable.Orientation.LEFT_RIGHT
        rightDrawable.shape = GradientDrawable.RECTANGLE
        leftDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
        leftDrawable.orientation = GradientDrawable.Orientation.RIGHT_LEFT
        leftDrawable.shape = GradientDrawable.RECTANGLE
    }
    
    fun setGradientColors(colors: IntArray) {
        rightDrawable.colors = colors
        leftDrawable.colors = colors
    }
    
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        rightDrawable.setBounds(0, 0, widthSize, heightSize)
        leftDrawable.setBounds(0, 0, widthSize, heightSize)
    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (visibility == GONE) return
        
        canvas.save()
        
        if (xMovement < width) {
            canvas.translate(xMovement, 0.0f)
            rightDrawable.draw(canvas)
            canvas.translate(-width.toFloat(), 0.0f)
            leftDrawable.draw(canvas)
        } else {
            canvas.translate(xMovement - width, 0.0f)
            leftDrawable.draw(canvas)
            canvas.translate(-width.toFloat(), 0.0f)
            rightDrawable.draw(canvas)
        }
        
        canvas.restore()
        
        xMovement += speedInPercentage * width / 100.0f
        if (xMovement >= width * 2.0f) xMovement = 0.0f
        
        invalidate()
    }
}