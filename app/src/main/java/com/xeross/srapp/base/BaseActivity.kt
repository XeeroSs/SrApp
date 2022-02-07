package com.xeross.srapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xeross.srapp.injection.ViewModelFactory

abstract class BaseActivity : AppCompatActivity() {
    
    abstract fun getFragmentId(): Int
    abstract fun getViewModelClass(): Class<*>
    abstract fun build()
    protected var vm: ViewModel? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getFragmentId())
        vm = configureViewModel()
        build()
    }
    
    @Suppress("UNCHECKED_CAST")
    private fun configureViewModel(): ViewModel {
        val factory = ViewModelFactory(this)
        return ViewModelProvider(this, factory).get(getViewModelClass() as Class<ViewModel>)
    }
    
    protected fun RecyclerView.setRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>, layout: LinearLayoutManager) {
        setHasFixedSize(true)
        layoutManager = layout
        itemAnimator = DefaultItemAnimator()
        this.adapter = adapter
    }
    
}