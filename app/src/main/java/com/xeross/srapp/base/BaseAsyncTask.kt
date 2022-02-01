package com.xeross.srapp.base

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class BaseAsyncTask {
    
    private val executor: Executor = Executors.newSingleThreadExecutor()
    private val handler: Handler = Handler(Looper.getMainLooper())
    
    interface Callback<T> {
        fun onComplete(result: T)
    }
    
    fun <T> executeAsync(callable: Callable<T>, callback: Callback<T>) {
        executor.execute {
            val result: T = callable.call()
            handler.post {
                callback.onComplete(result)
            }
        }
    }
    
}