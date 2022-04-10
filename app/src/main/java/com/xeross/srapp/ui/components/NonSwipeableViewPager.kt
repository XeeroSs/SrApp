package com.xeross.srapp.ui.components

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import androidx.viewpager.widget.ViewPager
import java.lang.reflect.Field;

class NonSwipeableViewPager : ViewPager {
    
    constructor(context: Context?) : super(context!!) {
        setScroller()
    }
    
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        setScroller()
    }
    
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return false
    }
    
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return false
    }
    
    private fun setScroller() {
        try {
            val viewpager: Class<*> = ViewPager::class.java
            val scroller: Field = viewpager.getDeclaredField("mScroller")
            scroller.isAccessible = true
            scroller.set(this, CustomScroller(context))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    inner class CustomScroller(context: Context?) :
        Scroller(context, DecelerateInterpolator()) {
        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            super.startScroll(startX, startY, dx, dy, 350 /*1 secs*/)
        }
    }
}