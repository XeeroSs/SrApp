package com.xeross.srapp.base

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xeross.srapp.utils.injection.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_header.*
import java.util.function.Function

abstract class BaseActivity : AppCompatActivity() {
    
    abstract fun getFragmentId(): Int
    abstract fun getViewModelClass(): Class<*>
    abstract fun setUp()
    abstract fun ui()
    abstract fun onClick()
    
    protected var vm: ViewModel? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getFragmentId())
        vm = provideViewModel()
        setUp()
        ui()
        onClick()
    }
    
    @Suppress("UNCHECKED_CAST")
    private fun provideViewModel(): ViewModel {
        val factory = ViewModelFactory(this)
        return ViewModelProvider(this, factory).get(getViewModelClass() as Class<ViewModel>)
    }
    
    protected fun setStatusBarTransparent() {
        // Status bar transparent
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
    }
    
    protected fun buildHeader(resIdTitle: Int, textSize: Float, function: Function<Void?, Boolean>? = null) {
        with(header_title) {
            post {
                this.textSize = textSize
            }
            this.text = resources.getString(resIdTitle)
        }
        header_toolbar?.setNavigationOnClickListener { _ ->
            function?.let { func ->
                func.apply(null).takeIf { it }?.let { _ ->
                    finish()
                }
                return@setNavigationOnClickListener
            }
            
            finish()
        }
    }
    
    protected inline fun <reified T : AppCompatActivity> goToActivity(finishActivity: Boolean = true) {
        val intent = Intent(this, T::class.java)
        if (finishActivity) intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        if (finishActivity) finish()
        startActivity(intent)
    }
    
}