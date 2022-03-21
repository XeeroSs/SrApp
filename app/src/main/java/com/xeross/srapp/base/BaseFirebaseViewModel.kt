package com.xeross.srapp.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseFirebaseViewModel<T : AppCompatActivity>(t: T) : ViewModel() {
    
    private var disposable: Disposable? = null
    
    fun build() {
    
    }
    
    protected fun getDisposable(): Disposable? = disposable
    
    init {
        build()
    }
    
    override fun onCleared() {
        super.onCleared()
        dispose()
    }
    
    /**
     * To call when activity is destroy/stop
     *
     */
    private fun dispose() {
        disposable?.takeIf {
            !it.isDisposed
        }?.dispose()
    }
    
    fun getDataFromCloudFirestore() {
    }
    
}