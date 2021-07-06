package com.xeross.srapp.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xeross.srapp.controller.main.MainViewModel
import com.xeross.srapp.helper.GoogleSheetHelper
import java.util.concurrent.Executor

class ViewModelFactory(private val executor: Executor) : ViewModelProvider.Factory {

    override fun <VM : ViewModel?> create(modelClass: Class<VM>): VM {
        when {
            getViewModel<MainViewModel>(modelClass) -> MainViewModel(executor)
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        } as VM
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    private inline fun <reified VM> getViewModel(modelClass: Class<*>): Boolean {
        return modelClass.isAssignableFrom(VM::class.java)
    }
}