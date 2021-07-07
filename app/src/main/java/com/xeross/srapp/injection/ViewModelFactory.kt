package com.xeross.srapp.injection

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xeross.srapp.controller.celeste.CelesteViewModel
import com.xeross.srapp.controller.main.MainViewModel
import java.util.concurrent.Executor

class ViewModelFactory(private val context:Context) : ViewModelProvider.Factory {

    override fun <VM : ViewModel?> create(modelClass: Class<VM>): VM {
        return when {
            getViewModel<MainViewModel>(modelClass) -> MainViewModel(context)
            getViewModel<CelesteViewModel>(modelClass) -> CelesteViewModel()
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        } as VM
    }

    private inline fun <reified VM> getViewModel(modelClass: Class<*>): Boolean {
        return modelClass.isAssignableFrom(VM::class.java)
    }
}