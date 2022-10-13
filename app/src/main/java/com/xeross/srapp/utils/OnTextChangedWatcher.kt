package com.xeross.srapp.utils

import android.text.Editable
import android.text.TextWatcher

/**
 * Is used only to save some lines, avoid necessarily calling "beforeTextChanged" and "afterTextChanged"
 */
abstract class OnTextChangedWatcher : TextWatcher {
    
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
    
    override fun afterTextChanged(p0: Editable?) {
    }
}