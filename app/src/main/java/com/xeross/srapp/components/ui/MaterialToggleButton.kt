package com.xeross.srapp.components.ui

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.xeross.srapp.R

class MaterialToggleButton : MaterialButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    
    override fun setEnabled(enabled: Boolean) {
    
        if(!enabled) {
            setTextColor(ContextCompat.getColor(context, R.color.on_surface))
            setBackgroundColor(ContextCompat.getColor(context, R.color.on_primary_variant))
            setIconTintResource(R.color.on_surface)
        }else {
            setTextColor(ContextCompat.getColor(context, R.color.white))
            setBackgroundColor(ContextCompat.getColor(context, R.color.negative))
            setIconTintResource(R.color.white)
        }
      
        super.setEnabled(enabled)
    }
}