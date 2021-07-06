package com.xeross.srapp.controller.main

import android.os.Bundle
import com.xeross.srapp.R
import com.xeross.srapp.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun getFragmentId() = R.layout.activity_main

    override fun getViewModelClass() = MainViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}