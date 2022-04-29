package com.xeross.srapp.components

import android.view.View
import android.widget.NumberPicker
import java.util.function.Function

class NumberPickerBuilder(numberPicker: View) {
    
    private var numberPicker: NumberPicker = numberPicker as NumberPicker
    
    fun max(max: Int): NumberPickerBuilder {
        numberPicker.maxValue = max
        return this@NumberPickerBuilder
    }
    
    fun min(min: Int = 0): NumberPickerBuilder {
        numberPicker.minValue = min
        return this@NumberPickerBuilder
    }
    
    fun format(function: Function<Int, String>): NumberPickerBuilder {
        numberPicker.setFormatter { digit -> return@setFormatter function.apply(digit) }
        return this@NumberPickerBuilder
    }
    
    fun build(): NumberPicker = numberPicker
}