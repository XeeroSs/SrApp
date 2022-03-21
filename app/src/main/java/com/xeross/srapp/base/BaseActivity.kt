package com.xeross.srapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xeross.srapp.utils.injection.ViewModelFactory

abstract class BaseActivity : AppCompatActivity() {
    
    abstract fun getFragmentId(): Int
    abstract fun getViewModelClass(): Class<*>
    abstract fun build()
    protected var vm: ViewModel? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getFragmentId())
        vm = provideViewModel()
        build()
    }
    
    @Suppress("UNCHECKED_CAST")
    private fun provideViewModel(): ViewModel {
        val factory = ViewModelFactory(this)
        return ViewModelProvider(this, factory).get(getViewModelClass() as Class<ViewModel>)
    }
    
}