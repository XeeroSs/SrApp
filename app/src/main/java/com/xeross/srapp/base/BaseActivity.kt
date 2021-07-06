package com.xeross.srapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xeross.srapp.injection.ViewModelFactory
import java.util.concurrent.Executors

abstract class BaseActivity : AppCompatActivity() {

    abstract fun getFragmentId(): Int
    abstract fun getViewModelClass(): Class<*>
    protected var viewModel: ViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getFragmentId())
        viewModel = configureViewModel()
    }

    @SuppressWarnings("unchecked")
    private fun configureViewModel(): ViewModel {
        val factory = ViewModelFactory(Executors.newSingleThreadExecutor())
        return ViewModelProvider(this, factory).get(getViewModelClass() as Class<ViewModel>)
    }

}