package com.xeross.srapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xeross.srapp.controller.celeste.CelesteViewModel
import com.xeross.srapp.injection.ViewModelFactory
import java.util.concurrent.Executors

abstract class BaseFragment : Fragment() {

    abstract fun getFragmentId(): Int
    abstract fun getSheetsName(): String

    // abstract fun getViewModelClass(): Class<*>
    private var viewModel: CelesteViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = configureViewModel().also {
            it.build(getSheetsName())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getFragmentId(), container, false)
    }

    @SuppressWarnings("unchecked")
    private fun configureViewModel(): CelesteViewModel {
        val factory = ViewModelFactory(Executors.newSingleThreadExecutor())
        return ViewModelProvider(this, factory).get(CelesteViewModel::class.java)
    }

}