package com.xeross.srapp.ui.categoryform.fragment.base

import androidx.fragment.app.Fragment

abstract class BaseCategoryFormFragment : Fragment() {
    
    abstract fun getExtra(): Pair<String, String>?
    abstract fun hasExtra(): Boolean
    abstract fun isNextValid(): Boolean
    
}