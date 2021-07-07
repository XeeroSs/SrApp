package com.xeross.srapp.controller.main

import androidx.lifecycle.ViewModel
import com.xeross.srapp.helper.http.RetrofitHelper
import com.xeross.srapp.helper.http.SrcApiService
import java.util.concurrent.Executor

class MainViewModel() : ViewModel() {

    private var api: SrcApiService? = null

    fun build() {
        api = RetrofitHelper.getClient().create(SrcApiService::class.java)
    }

}