package com.xeross.srapp.ui.categoryform.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseCategoryFormFragment<B : ViewBinding> : Fragment() {
    
    abstract fun getExtra(): Pair<String, String>?
    abstract fun hasExtra(): Boolean
    abstract fun isNextValid(): Boolean
    
    protected abstract fun setup()
    
    
    protected lateinit var binding: B
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        
        binding = attachViewBinding(inflater, container) as B
        setup()
        
        return binding.root
    }
    
    abstract fun attachViewBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding
}