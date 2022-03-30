package com.xeross.srapp.utils.extensions

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.xeross.srapp.R
import kotlin.math.cos
import kotlin.math.sin


object UIExtensions {
    
    fun TextView.setTintGradient(colors: IntArray) {
        val width = this.paint.measureText(this.text.toString())
        // Get gradient angle
        val angle = 45f
        val widthAngle = (sin(Math.PI * angle / 180) * width).toFloat()
        val heightAngle = (cos(Math.PI * angle / 180) * width).toFloat()
        
        val textShader: Shader = LinearGradient(0f, 0f, widthAngle, heightAngle, colors, null, Shader.TileMode.CLAMP)
        
        // Remove the "white cover" on text
        this.setTextColor(Color.WHITE)
        
        // Set gradient on text
        this.paint.shader = textShader
    }
    
    fun ImageView.setTintGradient(colors: IntArray, context: Context, cornerRadius: Float = 0f) {
        val gd = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM, colors)
        gd.cornerRadius = cornerRadius
        
        // TODO("make gradient method")
        this.setTint(R.color.purple_gradient)
    }
    
    fun ImageView.setTint(@ColorRes colorRes: Int) {
        ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(ContextCompat.getColor(context, colorRes)))
    }
    
    fun TextInputLayout.error(context: Context, resId: Int, vararg formatArgs: String) {
     //   this.errorContentDescription = context.resources.getString(resId, formatArgs)
        this.errorIconDrawable = null
        this.error = context.resources.getString(resId, formatArgs)
    }
    
    fun RecyclerView.setRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>, layout: LinearLayoutManager) {
        setHasFixedSize(true)
        layoutManager = layout
        itemAnimator = DefaultItemAnimator()
        this.adapter = adapter
    }
    
}