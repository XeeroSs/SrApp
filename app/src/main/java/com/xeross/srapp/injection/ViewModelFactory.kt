package com.xeross.srapp.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xeross.srapp.controller.celeste.CelesteViewModel
import com.xeross.srapp.controller.main.MainViewModel
import com.xeross.srapp.helper.GoogleSheetHelper
import java.util.concurrent.Executor

class ViewModelFactory(private val executor: Executor) : ViewModelProvider.Factory {

    override fun <VM : ViewModel?> create(modelClass: Class<VM>): VM {
        return when {
            getViewModel<MainViewModel>(modelClass) -> MainViewModel(executor)
            getViewModel<CelesteViewModel>(modelClass) -> CelesteViewModel()
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        } as VM
    }

    private inline fun <reified VM> getViewModel(modelClass: Class<*>): Boolean {
        return modelClass.isAssignableFrom(VM::class.java)
    }
}