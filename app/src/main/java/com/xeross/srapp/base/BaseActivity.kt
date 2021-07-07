package com.xeross.srapp.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xeross.srapp.injection.ViewModelFactory
import java.util.concurrent.Executors

abstract class BaseActivity : AppCompatActivity() {

    abstract fun getFragmentId(): Int
    abstract fun getViewModelClass(): Class<*>
    abstract fun build()
    protected var viewModel: ViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getFragmentId())
        viewModel = configureViewModel()
        build()
    }

    @Suppress("UNCHECKED_CAST")
    private fun configureViewModel(): ViewModel {
        val factory = ViewModelFactory(this)
        return ViewModelProvider(this, factory).get(getViewModelClass() as Class<ViewModel>)
    }

    protected fun RecyclerView.setRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>, isCustom: Boolean) {
        setHasFixedSize(true)
        if (!isCustom) layoutManager = LinearLayoutManager(context)
        itemAnimator = DefaultItemAnimator()
        this.adapter = adapter
    }

}